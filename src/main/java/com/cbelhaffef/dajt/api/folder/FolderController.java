package com.cbelhaffef.dajt.api.folder;

import com.cbelhaffef.dajt.api.user.UserService;
import com.cbelhaffef.dajt.enums.FolderPriority;
import com.cbelhaffef.dajt.enums.FolderStatus;
import com.cbelhaffef.dajt.exception.ResourceAlreadyAddedException;
import com.cbelhaffef.dajt.model.accused.Accused;
import com.cbelhaffef.dajt.model.action.Action;
import com.cbelhaffef.dajt.model.folder.Folder;
import com.cbelhaffef.dajt.model.folder.FolderListResponse;
import com.cbelhaffef.dajt.model.folder.FolderPriorityResponse;
import com.cbelhaffef.dajt.model.folder.FolderStatusResponse;
import com.cbelhaffef.dajt.model.office.Office;
import com.cbelhaffef.dajt.model.user.User;
import com.cbelhaffef.dajt.model.victim.Victim;
import com.cbelhaffef.dajt.repo.ActionRepo;
import com.cbelhaffef.dajt.repo.FolderRepo;
import com.google.common.collect.Sets;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = {"Folder"})
public class FolderController {

    @Autowired private FolderRepo folderRepo;

    @Autowired private UserService userService;

    @Autowired private ActionRepo actionRepo;

    @ApiOperation(value = "List of folders", response = FolderListResponse.class)
    @RequestMapping(value = "/folders", method = RequestMethod.GET , produces={"application/json; charset=UTF-8"})
    public FolderListResponse getFoldersByPage(
        @ApiParam(value = ""    )               @RequestParam(value = "page"  ,  defaultValue="0"   ,  required = false) Integer page,
        @ApiParam(value = "between 1 to 1000" ) @RequestParam(value = "size"  ,  defaultValue="10"  ,  required = false) Integer size,
        @RequestParam(value = "folderNumber"     , required = false) String folderNumber,
        @RequestParam(value = "office"      , required = false) Long office,
        @RequestParam(value = "status"      , required = false) FolderStatus status,
        @RequestParam(value = "accused"      , required = false) String accused,
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
        if (status != null)  { qry.setStatus(status); }

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
        return resp;
    }

    @RequestMapping(value="/folders", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Folder addFolder(@RequestBody Folder folder){
        Folder folderSaved = folderRepo.save(folder);
        return folderSaved;
    }

    @ApiOperation(value = "List of status", response = FolderStatusResponse.class)
    @RequestMapping(value = "/folders/status", method = RequestMethod.GET)
    public FolderStatusResponse getFoldersStatus(@RequestParam(value="name", required = false) String name) {
        FolderStatusResponse resp = new FolderStatusResponse();
        resp.setItems(Arrays.asList(FolderStatus.values()));
        return resp;
    }

    @ApiOperation(value = "List of priorities", response = FolderStatusResponse.class)
    @RequestMapping(value = "/folders/priorities", method = RequestMethod.GET)
    public FolderPriorityResponse getFoldersPriorities(@RequestParam(value="name", required = false) String name) {
        FolderPriorityResponse resp = new FolderPriorityResponse();
        resp.setItems(Arrays.asList(FolderPriority.values()));
        return resp;
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
        throws ResourceNotFoundException, ResourceNotFoundException {

        User userDb = userService.getUserById(userId);
        if(userDb == null){
            throw new ResourceNotFoundException("l'opérateur n'a pas été trouvé. Contactez votre Administrateur.",null);
        }

        if(foldersIds == null || foldersIds.isEmpty()){
            throw new ResourceNotFoundException("Aucun dossier n'a été séléctionné. Vérifier votre requête.",null);
        }

        List<Folder> foldersDb = folderRepo.findByIdIn(foldersIds);

        for(Folder f : foldersDb){
            f.setAssignee(userDb);
        }

        return foldersDb;
    }

    @Transactional
    @RequestMapping(value="/folders/{folderId}/actions", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Folder addAction(@PathVariable("folderId") Long folderId, @RequestBody Action action)
        throws ResourceNotFoundException{
        //check if actions is not already added
        //if not then added to list actions of folders and then leave;
        if(folderId == null){
            throw new ResourceNotFoundException("Aucun dossier n'a été séléctionné. Vérifier votre requête.",null);
        }

        Optional<Folder> optionalFolderDb = folderRepo.findById(folderId);
        if(!optionalFolderDb.isPresent()){
            throw new ResourceNotFoundException("Aucun dossier trouvé. Contactez votre administrateur.",null);
        }

        Folder folderDb = optionalFolderDb.get();
        if (folderDb.getActions().contains(action)) {
            throw new ResourceAlreadyAddedException("L'action à déja été traiter sur ce dossier");
        }
        folderDb.getActions().add(action);

        return folderDb;
    }

    @Transactional
    @ApiOperation(value = "Add action to list of folders", response = Folder.class)
    @RequestMapping(value="/folders/addAction/{actionId}", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Folder> addActionToListOfFolders(@PathVariable("actionId") Long actionId, @RequestBody List<Long> foldersIds)
        throws ResourceNotFoundException {

        Action actionDb = actionRepo.findOne(actionId);
        if(actionDb == null){
            throw new ResourceNotFoundException("l'action n'a pas été trouvée. Contactez votre Administrateur.",null);
        }

        if(foldersIds == null || foldersIds.isEmpty()){
            throw new ResourceNotFoundException("Aucun dossier n'a été séléctionné. Vérifier votre requête.",null);
        }

        List<Folder> foldersDb = folderRepo.findByIdIn(foldersIds);

        for(Folder f : foldersDb){
            f.getActions().add(actionDb);
            folderRepo.save(f);
        }

        return foldersDb;
    }

    @Transactional
    @ApiOperation(value = "Change  status to list of folders", response = Folder.class)
    @RequestMapping(value="/folders/changeStatus/{status}", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Folder> changeStatusToListOfFolders(@PathVariable("status") String status, @RequestBody List<Long> foldersIds)
        throws ResourceNotFoundException {

        FolderStatus statusEnum = FolderStatus.valueOf(status);

        if(statusEnum == null){
            throw new ResourceNotFoundException("le status n'a pas été trouvée. Contactez votre Administrateur.",null);
        }

        if(foldersIds == null || foldersIds.isEmpty()){
            throw new ResourceNotFoundException("Aucun dossier n'a été séléctionné. Vérifier votre requête.",null);
        }

        List<Folder> foldersDb = folderRepo.findByIdIn(foldersIds);

        for(Folder f : foldersDb){
            f.setStatus(statusEnum);
            folderRepo.save(f);
        }

        return foldersDb;
    }

}
