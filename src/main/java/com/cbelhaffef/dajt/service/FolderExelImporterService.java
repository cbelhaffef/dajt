package com.cbelhaffef.dajt.service;

import com.cbelhaffef.dajt.model.folder.Folder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Set;


public interface FolderExelImporterService {

    Set<Folder> doImport(File fileExel) throws FileNotFoundException;

}
