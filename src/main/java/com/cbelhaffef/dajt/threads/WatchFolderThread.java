package com.cbelhaffef.dajt.threads;

import com.cbelhaffef.dajt.service.WatcherFolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;

@Component
@Scope("prototype")
public class WatchFolderThread implements Runnable{

    private final static String PATH_FOLDER_TO_WATCH = "/mnt/import";

    @Autowired
    private WatcherFolderService watcherFolderService;

    @Override
    public void run() {
        try {
            watcherFolderService.initWatch(PATH_FOLDER_TO_WATCH);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
