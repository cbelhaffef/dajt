package com.cbelhaffef.dajt.service;

import java.io.FileNotFoundException;

public interface WatcherFolderService {

    void initWatch(String path) throws FileNotFoundException;

}
