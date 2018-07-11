package com.cbelhaffef.dajt.service.impl;

import com.cbelhaffef.dajt.service.ExelFileImporterService;
import com.cbelhaffef.dajt.service.WatcherFolderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.text.ParseException;
import java.util.Arrays;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

@Service
@Slf4j
public class WatcherFolderServiceImpl implements WatcherFolderService {

    private WatchService watchService;

    public final static String EXEL_FILE_EXT = "xlsx";

    @Autowired
    private ExelFileImporterService exelFileImporterService;

    public WatcherFolderServiceImpl() throws IOException {
        watchService = FileSystems.getDefault().newWatchService();
    }

    @Override
    public void initWatch(String path) throws IOException {
        Path dir = Paths.get(path);
        if(!Files.exists(dir)){
            dir.toFile().mkdirs();
        }

        // detect of files already on import directory
        Arrays.stream(dir.toFile().listFiles())
            .filter(f->f.getName().endsWith(EXEL_FILE_EXT))
            .forEach(f-> {
                try {
                    exelFileImporterService.doImport(f,true);
                } catch (IOException e) {
                    log.error("Error pour l'import du fichier " +f.getName());
                    e.printStackTrace();
                }
            });

        // watch path
        try {
            dir.register(watchService,
                ENTRY_CREATE,
                ENTRY_MODIFY);

            WatchKey key;
            while ((key = watchService.take()) != null) {

                for (WatchEvent<?> event : key.pollEvents()) {
                   log.info("Event kind:" + event.kind() + ". File affected: " + event.context() + ".");

                    String pathFile = dir + File.separator + event.context().toString();

                    String extesion = FilenameUtils.getExtension(pathFile);
                    if (extesion == null || !extesion.equals(EXEL_FILE_EXT)){
                        log.warn("L'extension du fichier : "+ event.context().toString() + " n'est pas pris en compte");
                    }else{
                        File file = new File(pathFile);
                        exelFileImporterService.doImport(file,true);
                    }
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
