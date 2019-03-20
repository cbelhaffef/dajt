package com.cbelhaffef.dajt.threads;

import com.cbelhaffef.dajt.service.WatcherFolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Scope("prototype")
public class WatchFolderThread implements Runnable{

    @Value("${folder.watch.path}")
    private String folderWatchPath;

    @Autowired
    private WatcherFolderService watcherFolderService;

    @Override
    public void run() {
        try {
            watcherFolderService.initWatch(folderWatchPath);
        } catch (IOException | InterruptedException e ) {
            e.printStackTrace();
        }

    }

}
