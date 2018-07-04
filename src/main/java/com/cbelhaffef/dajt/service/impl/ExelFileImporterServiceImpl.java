package com.cbelhaffef.dajt.service.impl;

import com.cbelhaffef.dajt.enums.FolderColumnImport;
import com.cbelhaffef.dajt.model.court.Court;
import com.cbelhaffef.dajt.model.folder.Folder;
import com.cbelhaffef.dajt.model.guilty.Guilty;
import com.cbelhaffef.dajt.model.importfile.Import;
import com.cbelhaffef.dajt.model.importfile.ImportStatusEnum;
import com.cbelhaffef.dajt.model.office.Office;
import com.cbelhaffef.dajt.model.victim.Victim;
import com.cbelhaffef.dajt.repo.CourtRepo;
import com.cbelhaffef.dajt.repo.FolderRepo;
import com.cbelhaffef.dajt.repo.ImportRepo;
import com.cbelhaffef.dajt.repo.OfficeRepo;
import com.cbelhaffef.dajt.service.ExelFileImporterService;
import com.google.common.collect.Sets;
import com.google.j2objc.annotations.AutoreleasePool;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static com.cbelhaffef.dajt.model.importfile.ImportStatusEnum.IN_PROGRESS;
import static com.cbelhaffef.dajt.model.importfile.ImportStatusEnum.ON_ERROR;

@Service
@Slf4j
public class ExelFileImporterServiceImpl implements ExelFileImporterService {

    @Autowired
    private FolderRepo folderRepo;

    @Autowired
    private OfficeRepo officeRepo;

    @Autowired
    private CourtRepo courtRepo;

    @Autowired
    private ImportRepo importRepo;

    private final static String ARCHIVE_DIRECTORY = "/mnt/import/archives";

    @Override
    @Transactional
    public Set<Folder> doImport(File fileExel) throws IOException {

        Set<Folder> folders = new TreeSet<>();
        Path filePath = null;
        Path filePathTemp = null;

        Import imp = new Import();
        imp.setStartDate(new Date());
        imp.setFileName(fileExel.getAbsolutePath());

        Optional<Import> importDb = importRepo.findByFileName(fileExel.getAbsolutePath());

        if(importDb.isPresent()){
            imp.setStatus(ON_ERROR);
            imp.setEndDate(new Date());
            String msg = "Import a échoué - le fichier a été déja importé dans le system.";
            imp.setMessage(msg);
            imp = importRepo.save(imp);
            throw  new FileAlreadyExistsException(msg);
        }

        if(!fileExel.exists()) {
            imp.setStatus(ON_ERROR);
            imp.setEndDate(new Date());
            String msg = "Import a échoué -  Le fichier n'existe pas";
            imp.setMessage(msg);
            imp = importRepo.save(imp);
            throw  new FileNotFoundException(msg);
        }else {
            imp.setStatus(IN_PROGRESS);
            filePath = fileExel.toPath();
            imp.setFileSize(Files.size(filePath));
            imp = importRepo.save(imp);
        }

        try {
            // fileName
            String fileName = fileExel.getName();
            String[] args = fileName.split("_");
            if(args.length < 3 ){
                throw new FileFormatException("Nom du fichier ne respecte pas le pattern : type_numDossier_annee_numbFile");
            }
            // officeNumber
            String officeNumber = args[1];
            if(officeNumber.length() > 2){
                throw new FileFormatException("le nom du bureau n'est pas conforme");
            }
            Long OfficeId = new Long(officeNumber.substring(1));
            Office office = officeRepo.getOne(OfficeId);

            log.info("=============Debut de l'importation pour le bureau '" + office.getName()+ "' du fichier : " + fileExel.getAbsolutePath());


            FileInputStream excelFile = new FileInputStream(fileExel);

            //change file to temp
            filePathTemp = filePath.resolveSibling(filePath.getFileName()+"Temp");
            Files.move(filePath, filePathTemp);

            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0); // choose year on sheet
            Iterator<Row> iterator = datatypeSheet.iterator();

            //Skip the header (first row)
            if (iterator.hasNext()) iterator.next();

            while (iterator.hasNext()) {

                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();

                Folder folder = new Folder();

                for(int i = 0 ; cellIterator.hasNext() ; i++){

                    Cell currentCell = cellIterator.next();

                    if (i == FolderColumnImport.NUMBER.getValue() && currentCell.getCellTypeEnum() == CellType.STRING) {
                        String number = currentCell.getStringCellValue();

                        Optional<Folder> folderDb = folderRepo.findByNumber(number);
                        if(folderDb.isPresent()){
                            break;
                        }

                        folder.setNumber(number);
                    }

                    if (i == FolderColumnImport.DIRECTION_NUMBER.getValue() && currentCell.getCellTypeEnum() == CellType.STRING) {
                        folder.setDirectionNumber(currentCell.getStringCellValue());
                    }

                    if (i == FolderColumnImport.VICTIMS.getValue() && currentCell.getCellTypeEnum() == CellType.STRING) {
                        String stringVictims = currentCell.getStringCellValue();
                        Set<Victim> victims;
                        if(stringVictims.contains("\n")){
                            String[] arr = stringVictims.split("\n");
                            victims = Arrays.stream(arr).map(a -> new Victim(a)).collect(Collectors.toSet());
                        }else{
                            victims = Sets.newHashSet(new Victim(stringVictims));
                        }
                        folder.setVictims(victims);
                    }

                    if (i == FolderColumnImport.GUILTIES.getValue() && currentCell.getCellTypeEnum() == CellType.STRING) {
                        String stringGuilties = currentCell.getStringCellValue();
                        Set<Guilty> guilties;
                        if(stringGuilties.contains("\n")){
                            String[] arr = stringGuilties.split("\n");
                            guilties = Arrays.stream(arr).map(a -> new Guilty(a)).collect(Collectors.toSet());
                        }else{
                            guilties = Sets.newHashSet(new Guilty(stringGuilties));
                        }
                        folder.setGuilties(guilties);
                    }

                    if (i == FolderColumnImport.OFFENCE.getValue() && currentCell.getCellTypeEnum() == CellType.STRING) {
                        folder.setOffence(currentCell.getStringCellValue());
                    }

                    if (i == FolderColumnImport.COURT.getValue() && currentCell.getCellTypeEnum() == CellType.STRING) {
                        String courtName = currentCell.getStringCellValue();
                        Optional<Court> court = courtRepo.findByName(courtName);
                         if(!court.isPresent()){
                             court = Optional.of(new Court(currentCell.getStringCellValue()));
                         }
                        folder.setCourt(court.get());
                    }

                    if (i == FolderColumnImport.SENDING_TYPE.getValue() && currentCell.getCellTypeEnum() == CellType.STRING) {
                        folder.setSendingType(currentCell.getStringCellValue());
                    }

                    folder.setOffice(office);
                }
                folderRepo.save(folder);
            }

            imp.setEndDate(new Date());
            imp.setMessage("le fichier " + filePath.getFileName() + " a été importé avec succées");
            imp.setStatus(ImportStatusEnum.FINICHED);
            imp = importRepo.save(imp);
            File dirArchive = new File(ARCHIVE_DIRECTORY);
            if(!dirArchive.exists()){
                dirArchive.mkdirs() ;
            }
            // Move import file into archive directory when finished
            Files.move(filePathTemp,  Paths.get(dirArchive.getAbsolutePath() + File.separator + filePathTemp.getFileName()));

        } catch ( IOException e ) {
            log.error("============Import fichier " + fileExel.getAbsolutePath() + "a échoué." ,e);
            imp.setStatus(ImportStatusEnum.ON_ERROR);
            imp.setEndDate(new Date());
            Path fileError = Paths.get(filePath.toString() + "ERROR");
            if(filePathTemp != null){
                Files.move(filePathTemp, fileError);
            }else{
                Files.move(filePath, fileError);
            }
            imp = importRepo.save(imp);
        }
        return folders;
    }
}
