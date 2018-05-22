package com.cbelhaffef.dajt.service.impl;

import com.cbelhaffef.dajt.enums.FolderColumnImport;
import com.cbelhaffef.dajt.model.court.Court;
import com.cbelhaffef.dajt.model.folder.Folder;
import com.cbelhaffef.dajt.model.guilty.Guilty;
import com.cbelhaffef.dajt.model.victim.Victim;
import com.cbelhaffef.dajt.repo.FolderRepo;
import com.cbelhaffef.dajt.service.FolderExelImporterService;
import com.google.common.collect.Sets;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FolderExelImporterServiceImpl implements FolderExelImporterService{

    @Autowired
    private FolderRepo folderRepo;

    @Override
    public Set<Folder> doImport(File fileExel) throws FileNotFoundException {
        Set<Folder> folders = new TreeSet<>();
        try {
            FileInputStream excelFile = new FileInputStream(fileExel);
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
                    //getCellTypeEnum shown as deprecated for version 3.15
                    //getCellTypeEnum ill be renamed to getCellType starting from version 4.0
                    if (i == FolderColumnImport.NUMBER.getValue() && currentCell.getCellTypeEnum() == CellType.STRING) {
                        folder.setNumber(currentCell.getStringCellValue());
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
                        folder.setCourt(new Court(currentCell.getStringCellValue()));
                    }

                    if (i == FolderColumnImport.SENDING_TYPE.getValue() && currentCell.getCellTypeEnum() == CellType.STRING) {
                        folder.setSendingType(currentCell.getStringCellValue());
                    }
                }
                folderRepo.save(folder);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return folders;
    }
}
