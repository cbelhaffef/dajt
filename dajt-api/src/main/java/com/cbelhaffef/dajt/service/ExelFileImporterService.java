package com.cbelhaffef.dajt.service;


import com.cbelhaffef.dajt.api.folder.Folder;

import java.io.File;
import java.util.Set;


public interface ExelFileImporterService {

    Set<Folder> doImport(File fileExel);
    Set<Folder> doImport(File fileExel, boolean reversedKey);

}
