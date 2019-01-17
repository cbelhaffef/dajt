package com.cbelhaffef.dajt.api.folder;

import com.cbelhaffef.dajt.api.user.UserService;

import com.cbelhaffef.dajt.dao.enums.StatusFolder;
import com.cbelhaffef.dajt.exception.ResourceAlreadyAddedException;
import com.cbelhaffef.dajt.exception.ResourceNotFoundException;
import com.cbelhaffef.dajt.dao.entities.Folder;
import com.cbelhaffef.dajt.dao.entities.Status;
import com.cbelhaffef.dajt.dao.entities.Accused;
import com.cbelhaffef.dajt.dao.entities.Action;
import com.cbelhaffef.dajt.dao.entities.Court;
import com.cbelhaffef.dajt.models.folder.FolderListResponse;
import com.cbelhaffef.dajt.dao.entities.Office;
import com.cbelhaffef.dajt.dao.entities.User;
import com.cbelhaffef.dajt.dao.entities.Victim;
import com.cbelhaffef.dajt.dao.repositories.*;
import com.google.common.collect.Sets;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javassist.tools.web.BadHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = {"Folder"})
public class FolderController {

    @Autowired private FolderRepo folderRepo;

    @Autowired private UserService userService;

    @Autowired private ActionRepo actionRepo;

    @Autowired private StatusRepo statusRepo;

    @Autowired private VictimRepo victimRepo;

    @Autowired private AccusedRepo accusedRepo;

    @Autowired private CourtRepo courtRepo;

    @ApiOperation(value = "List of folders", response = FolderListResponse.class)
    @RequestMapping(value = "/folders", method = RequestMethod.GET , produces={"application/json; charset=UTF-8"})
    public FolderListResponse getFoldersByPage(
        @ApiParam(value = "0 as default value")@RequestParam(value = "page",  defaultValue="0" ,  required = false) Integer page,
        @ApiParam(value = "between 1 to 1000") @RequestParam(value = "size"  ,  defaultValue="10",  required = false) Integer size,
        @RequestParam(value = "folderNumber", required = false) String folderNumber,
        @RequestParam(value = "office"      , required = false) Long office,
        @RequestParam(value = "status"      , required = false) Long status,
        @RequestParam(value = "accused"     , required = false) String accused,
        @RequestParam(value = "victim"      , required = false) String victim,
        Pageable pageable
    ) {

        FolderListResponse resp = new FolderListResponse();
        Folder qry = new Folder();
        qry.setStatus(null);
        qry.setPriority(null);
        if (folderNumber != null)  { qry.setNumber(folderNumber); }
        Office officeObj = new Office();
        officeObj.setId(office);
        if (office != null)  { qry.setOffice(officeObj); }
        Status statusObj = new Status();
        statusObj.setId(status);
        if (status != null)  { qry.setStatus(statusObj); }

        Victim victimObj = new Victim();
        victimObj.setName(victim);
        if (victim != null)        { qry.setVictims(Sets.newHashSet(victimObj)); };

        Accused guiltyObj = new Accused();
        guiltyObj.setName(accused);
        if (accused != null)        { qry.setAccused(Sets.newHashSet(guiltyObj)); };

        Page<Folder> pg = folderRepo.findByFilter(qry,pageable);
        resp.setPageStats(pg, true);
        resp.setItems(pg.getContent());
        resp.setPageSize(size);
        resp.setCurrentPageNumber(page);
        return resp;
    }

    @Transactional
    @RequestMapping(value="/folders", method = RequestMethod.POST ,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Folder addFolder(@RequestBody Folder folder){
        folder.getVictims().forEach(v -> v.setFolder(folder));
        folder.getAccused().forEach(a -> a.setFolder(folder));
        if(folder.getStatus() != null && folder.getStatus().getCode().equals(StatusFolder.CLOSE.name())){
            folder.setClosed(new Date());
        }else{
            folder.setClosed(null);
        }
        if(folder.getCourt() != null && folder.getCourt().getId() != null){
            Court court = courtRepo.findOne(folder.getCourt().getId());
            if(court != null){
                court.getFolders().add(folder);
                folder.setCourt(court);
            }
        }

        Folder folderSaved = folderRepo.save(folder);
        return folderSaved;
    }

    @RequestMapping(value="/folders", method = RequestMethod.PUT ,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Folder updateFolder(@RequestBody Folder folder) throws BadHttpRequest {
        if(folder == null && folder.getId() == null) {
            throw new BadHttpRequest();
        }
        Optional<Folder> folderDb = folderRepo.findById(folder.getId());
        if(!folderDb.isPresent()){
            throw new ResourceNotFoundException("Pas de dossier pour l'id : " + folder.getId());
        }
        if(folder.getStatus() != null && folder.getStatus().getCode().equals(StatusFolder.CLOSE.name())){
            folder.setClosed(new Date());
        }else{
            folder.setClosed(null);
        }
        Folder folderSaved = folderRepo.save(folder);
        return folderSaved;
    }

    @ApiOperation(value = "Order Details", response = Folder.class)
    @RequestMapping(value = "/folders/{id}", method = RequestMethod.GET)
    public Folder getFolderDetail( @PathVariable("id") Long id) {
        Optional<Folder> folder = folderRepo.findById(id);
        return folder.isPresent() ? folder.get() : null;
    }

    @Transactional
    @ApiOperation(value = "Assign user to list of folder", response = Folder.class)
    @RequestMapping(value="/folders/assign/{userId}", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Folder> assignUser(@PathVariable("userId") Long userId, @RequestBody List<Long> foldersIds)
        throws ResourceNotFoundException {

        User userDb = userService.getUserById(userId);
        if(userDb == null){
            throw new ResourceNotFoundException("l'opérateur n'a pas été trouvé. Contactez votre Administrateur.");
        }

        if(foldersIds == null || foldersIds.isEmpty()){
            throw new ResourceNotFoundException("Aucun dossier n'a été séléctionné. Vérifier votre requête.");
        }

        List<Folder> foldersDb = folderRepo.findByIdIn(foldersIds);

        for(Folder f : foldersDb){
            f.setAssignee(userDb);
        }

        return foldersDb;
    }

    @Transactional
    @ApiOperation(value = "Add action to list of folders", response = Folder.class)
    @RequestMapping(value="/folders/addAction/{actionCode}", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Folder> addActionToFolders(@PathVariable("actionCode") String actionCode, @RequestBody List<Long> foldersIds)
        throws ResourceNotFoundException {

        if(foldersIds == null || foldersIds.isEmpty()){
            throw new ResourceNotFoundException("Aucun dossier n'a été séléctionné. Vérifier votre requête.");
        }

        Optional<Action> actionDb = actionRepo.findByCode(actionCode);
        if(!actionDb.isPresent()){
            throw new ResourceNotFoundException("l'action n'a pas été trouvée. Contactez votre Administrateur.");
        }

        List<Folder> foldersDb = folderRepo.findByIdIn(foldersIds);

        for(Folder f : foldersDb){
            f.getActions().add(actionDb.get());
            folderRepo.save(f);
        }

        return foldersDb;
    }

    @Transactional
    @ApiOperation(value = "Add victim to folder", response = Folder.class)
    @RequestMapping(value="/folders/{folderId}/addVictim", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Folder addVictimToFolder(@PathVariable("folderId") Long folderId,@RequestBody Victim victim)
        throws ResourceNotFoundException {

        if(folderId == null){
            throw new ResourceNotFoundException("Aucun dossier n'a été séléctionné. Vérifier votre requête.");
        }

        Folder folderDb = folderRepo.findOne(folderId);
        if(folderDb == null){
            throw new ResourceNotFoundException("le dossier n'a pas été trouvée. Contactez votre Administrateur.");
        }

        if(folderDb.getVictims().contains(victim)){
          throw  new ResourceAlreadyAddedException("la victime : " + victim.getName() + " a déja été rajouter au dossier : " + folderDb.getNumber());
        };
        folderDb = setUpdaterFromAuthToken(folderDb);

        victim.setFolder(folderDb);
        victim = victimRepo.save(victim);
        folderDb.getVictims().add(victim);
        folderRepo.save(folderDb);
        return folderDb;
    }

    @Transactional
    @ApiOperation(value = "remove victim from folder", response = Folder.class)
    @RequestMapping(value="/folders/{folderId}/removeVictim/{victimId}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Folder removeVictimFromFolder(@PathVariable("folderId") Long folderId, @PathVariable("victimId") Long victimId)
        throws ResourceNotFoundException {

        if(folderId == null){
            throw new ResourceNotFoundException("Aucun dossier n'a été séléctionné. Vérifier votre requête.");
        }

        Folder folderDb = folderRepo.findOne(folderId);
        if(folderDb == null){
            throw new ResourceNotFoundException("le dossier n'a pas été trouvée. Contactez votre Administrateur.");
        }

        folderDb = setUpdaterFromAuthToken(folderDb);

        folderDb.getVictims().removeIf(v -> v.getId() == victimId );
        victimRepo.delete(victimId);
        folderDb = folderRepo.save(folderDb);
        return folderDb;
    }

    @Transactional
    @ApiOperation(value = "Add accused to folder", response = Folder.class)
    @RequestMapping(value="/folders/{folderId}/addAccused", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Folder addAccusedToFolder(@PathVariable("folderId") Long folderId,@RequestBody Accused accused)
        throws ResourceNotFoundException {

        if(folderId == null){
            throw new ResourceNotFoundException("Aucun dossier n'a été séléctionné. Vérifier votre requête.");
        }

        Folder folderDb = folderRepo.findOne(folderId);
        if(folderDb == null){
            throw new ResourceNotFoundException("le dossier n'a pas été trouvée. Contactez votre Administrateur.");
        }

        if(folderDb.getAccused().contains(accused)) {
            throw  new ResourceAlreadyAddedException("l'accusé : " + accused.getName() + " a déja été rajouter au dossier : " + folderDb.getNumber());
        };

        folderDb = setUpdaterFromAuthToken(folderDb);

        accused.setFolder(folderDb);
        accused = accusedRepo.save(accused);
        folderDb = folderRepo.save(folderDb);
        folderDb.getAccused().add(accused);
        return folderDb;
    }

    @Transactional
    @ApiOperation(value = "remove accused from folder", response = Folder.class)
    @RequestMapping(value="/folders/{folderId}/removeAccused/{victimId}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Folder removeAccusedFromFolder(@PathVariable("folderId") Long folderId, @PathVariable("accusedId") Long accusedId)
        throws ResourceNotFoundException {

        if(folderId == null){
            throw new ResourceNotFoundException("Aucun dossier n'a été séléctionné. Vérifier votre requête.");
        }

        Folder folderDb = folderRepo.findOne(folderId);
        if(folderDb == null){
            throw new ResourceNotFoundException("le dossier n'a pas été trouvée. Contactez votre Administrateur.");
        }

        folderDb = setUpdaterFromAuthToken(folderDb);

        folderDb.getAccused().removeIf(a -> a.getId() == accusedId );
        accusedRepo.delete(accusedId);

        folderDb = folderRepo.save(folderDb);

        return folderDb;
    }

    @Transactional
    @ApiOperation(value = "Add action to folder", response = Folder.class)
    @RequestMapping(value="/folders/{folderId}/addAction", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Folder addActionToFolder(@PathVariable("folderId") Long folderId,@RequestBody Action action)
        throws ResourceNotFoundException {

        if(folderId == null){
            throw new ResourceNotFoundException("Aucun dossier n'a été séléctionné. Vérifier votre requête.");
        }

        Folder folderDb = folderRepo.findOne(folderId);
        if(folderDb == null){
            throw new ResourceNotFoundException("le dossier n'a pas été trouvée. Contactez votre Administrateur.");
        }

        if(folderDb.getActions().contains(action)) {
            throw  new ResourceAlreadyAddedException("l'action : " + action.getName() + " a déja été rajouter au dossier : " + folderDb.getNumber());
        };

        folderDb = setUpdaterFromAuthToken(folderDb);

        folderDb.getActions().add(action);
        folderDb = folderRepo.save(folderDb);
        return folderDb;
    }

    @Transactional
    @ApiOperation(value = "remove action from folder", response = Folder.class)
    @RequestMapping(value="/folders/{folderId}/removeAction/{actionId}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Folder removeActionFromFolder(@PathVariable("folderId") Long folderId, @PathVariable("actionId") Long actionId)
        throws ResourceNotFoundException {

        if(folderId == null){
            throw new ResourceNotFoundException("Aucun dossier n'a été séléctionné. Vérifier votre requête.");
        }

        Folder folderDb = folderRepo.findOne(folderId);
        if(folderDb == null){
            throw new ResourceNotFoundException("le dossier n'a pas été trouvée. Contactez votre Administrateur.");
        }

        folderDb = setUpdaterFromAuthToken(folderDb);

        folderDb.getActions().removeIf(a -> a.getId() == actionId );
        folderDb = folderRepo.save(folderDb);

        return folderDb;
    }

    @Transactional
    @ApiOperation(value = "Change  status to list of folders", response = Folder.class)
    @RequestMapping(value="/folders/changeStatus/{status}", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Folder> changeStatusOfFolders(@PathVariable("status") String statusCode, @RequestBody List<Long> foldersIds)
        throws ResourceNotFoundException {

        if(foldersIds == null || foldersIds.isEmpty()){
            throw new ResourceNotFoundException("Aucun dossier n'a été séléctionné. Vérifier votre requête.");
        }

        Optional<Status> statusDb = statusRepo.findByCode(statusCode);
        if(!statusDb.isPresent()){
            throw  new ResourceNotFoundException("Aucun status existe pour le code : " + statusCode);
        }

        List<Folder> foldersDb = folderRepo.findByIdIn(foldersIds);

        for(Folder f : foldersDb){
            f.setStatus(statusDb.get());
            if(statusDb.get().getCode().equals(StatusFolder.CLOSE.name())){
                f.setClosed(new Date());
            }else{
                f.setClosed(null);
            }
            folderRepo.save(f);
        }

        return foldersDb;
    }

    @Transactional
    @ApiOperation(value = "Add action to folder", response = Folder.class)
    @RequestMapping(value="/folders/{folderId}/assign", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Folder assignToFolder(@PathVariable("folderId") Long folderId,@RequestBody User user)
        throws ResourceNotFoundException {

        if(folderId == null){
            throw new ResourceNotFoundException("Aucun dossier n'a été séléctionné. Vérifier votre requête.");
        }

        Folder folderDb = folderRepo.findOne(folderId);
        if(folderDb == null){
            throw new ResourceNotFoundException("le dossier n'a pas été trouvée. Contactez votre Administrateur.");
        }

        User userDb = userService.getUserById(user.getUserId());
        if(userDb == null){
            throw new ResourceNotFoundException("Aucun utilisateur existe pour le nom : " + user.getUsername());
        }

        folderDb = setUpdaterFromAuthToken(folderDb);

        folderDb.setAssignee(user);

        folderDb = folderRepo.save(folderDb);
        return folderDb;
    }

    private Folder setUpdaterFromAuthToken(Folder folder){
        Authentication authToken = SecurityContextHolder.getContext().getAuthentication();
        String username = (String)authToken.getPrincipal();
        User user = userService.getUserInfoByUsername(username);
        if(user != null){
            folder.setUpdater(user);
        }
        return folder;
    }
}
