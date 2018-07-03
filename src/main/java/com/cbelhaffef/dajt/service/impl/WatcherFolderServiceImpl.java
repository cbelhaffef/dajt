package com.cbelhaffef.dajt.service.impl;

import com.cbelhaffef.dajt.service.ExelFileImporterService;
import com.cbelhaffef.dajt.service.WatcherFolderService;
import org.apache.commons.io.FilenameUtils;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

@Service
public class WatcherFolderServiceImpl implements WatcherFolderService {

    private WatchService watchService;

    @Autowired
    private ExelFileImporterService fileImporterService;

    public WatcherFolderServiceImpl() throws IOException {
        watchService = FileSystems.getDefault().newWatchService();
    }

    @Override
    public void initWatch(String path) throws FileNotFoundException {
        Path dir = Paths.get(path);
        if(!Files.exists(dir)){
            throw new FileNotFoundException("Le dossier d'import n'existe pas.");
        }
        try {
            dir.register(watchService,
                ENTRY_CREATE,
                ENTRY_MODIFY);

            WatchKey key;
            while ((key = watchService.take()) != null) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    System.out.println("Event kind:" + event.kind() + ". File affected: " + event.context() + ".");

                    String pathFile = dir + File.separator + event.context().toString();

                    String extesion = FilenameUtils.getExtension(pathFile);
                    if (!extesion.equals("xlsx")){
                        throw  new FileFormatException("l'extension du fichier n'est pas supporter");
                    }

                    File file = new File(pathFile);

                    fileImporterService.doImport(file);
                }
                key.reset();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
