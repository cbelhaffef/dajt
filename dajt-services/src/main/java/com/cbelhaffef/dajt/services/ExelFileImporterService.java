package com.cbelhaffef.dajt.services;

import com.cbelhaffef.dajt.entities.Folder;

import java.io.File;
import java.util.Set;


public interface ExelFileImporterService {

    Set<Folder> doImport(File fileExel);
    Set<Folder> doImport(File fileExel, boolean reversedKey);

}
