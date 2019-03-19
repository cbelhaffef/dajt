package com.cbelhaffef.dajt.service;

import java.io.IOException;

public interface WatcherFolderService {

    void initWatch(String path) throws IOException, InterruptedException;

}
