package com.cbelhaffef.services;

import java.io.IOException;

public interface WatcherFolderService {

    void initWatch(String path) throws IOException, InterruptedException;

}
