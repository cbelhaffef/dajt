package com.cbelhaffef.dajt.service.impl;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cbelhaffef.dajt.service.ExelFileImporterService;
import com.cbelhaffef.dajt.service.WatcherFolderService;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

@Service
@Slf4j
public class WatcherFolderServiceImpl implements WatcherFolderService {

    private static Logger LOG = LoggerFactory.getLogger(WatcherFolderServiceImpl.class);

    private WatchService watchService;

    public final static String EXEL_FILE_EXT = "xlsx";

    @Autowired
    private ExelFileImporterService exelFileImporterService;

    public WatcherFolderServiceImpl() throws IOException {
        watchService = FileSystems.getDefault().newWatchService();
    }

    @Override
    public void initWatch(String path) {
        Path dir = Paths.get(path);
        // check imports folder
        if(!Files.exists(dir)){
            // create it if not exist
            dir.toFile().mkdirs();
        }

        // detect of files already on imports directory
        Arrays.stream(dir.toFile().listFiles())
            .filter(f->f.getName().endsWith(EXEL_FILE_EXT))
            .forEach(f-> {
                exelFileImporterService.doImport(f,true);
            });

        // watch path directory
        try {
            LOG.info("log");
            dir.register(watchService, ENTRY_CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        WatchKey key = null;
        while (true) {
            try {
                key = watchService.take();
                if (key != null) {
                    for (WatchEvent<?> event : key.pollEvents()) {
                        WatchEvent.Kind<?> kind = event.kind();
                        LOG.info("Event kind:" + kind + ". File affected: " + event.context() + ".");

                        // This key is registered only
                        // for ENTRY_CREATE, ENTRY_MODIFY events,
                        // but an OVERFLOW event can
                        // occur regardless if events
                        // are lost or discarded.
                        if (kind == OVERFLOW) {
                            continue;
                        }
                        String pathFile = dir + File.separator + event.context().toString();

                        String extesion = FilenameUtils.getExtension(pathFile);
                        if (extesion != null && extesion.equals(EXEL_FILE_EXT)) {
                            File file = new File(pathFile);
                            exelFileImporterService.doImport(file, true);
                        }
                    }
                }
            } catch (InterruptedException e) {
                LOG.error("Une erreur c'est produite au court de la surveillance du dossier");
                e.printStackTrace();
            } finally {
                /*
                    Reset the key -- this step is critical to receive
                    further watch events. If the key is no longer valid, the directory
                    is inaccessible so exit the loop.
                 */
                final boolean valid = key != null && key.reset();
                if (!valid) {
                    LOG.warn("Directory key is no longer valid. Quitting watcher service");
                }
            }
        }
    }
}
