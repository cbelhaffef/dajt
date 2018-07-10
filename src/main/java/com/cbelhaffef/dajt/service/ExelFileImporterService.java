package com.cbelhaffef.dajt.service;

import com.cbelhaffef.dajt.model.folder.Folder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.IllegalFormatException;
import java.util.Set;


public interface ExelFileImporterService {

    Set<Folder> doImport(File fileExel) throws IOException;
    Set<Folder> doImport(File fileExel, boolean reversedKey) throws IOException;

}
