package com.cbelhaffef.dajt.service.impl;

import com.cbelhaffef.dajt.model.folder.Folder;
import com.cbelhaffef.dajt.service.FolderExelImporterService;
import com.cbelhaffef.dajt.service.model.FolderExel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Service
public class FolderExelImporterServiceImpl implements FolderExelImporterService{


    @Override
    public Set<Folder> doImport(File fileExel) throws FileNotFoundException {
        Set<Folder> folders = new HashSet<>();
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

                while (cellIterator.hasNext()) {

                    Cell currentCell = cellIterator.next();
                    //getCellTypeEnum shown as deprecated for version 3.15
                    //getCellTypeEnum ill be renamed to getCellType starting from version 4.0
                    if (currentCell.getCellTypeEnum() == CellType.STRING) {
                        folder.setNumber(currentCell.getStringCellValue());
                    }
                    break; // FIXME get first cell, it's temporary
                }
                folders.add(folder);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return folders;
    }
}
