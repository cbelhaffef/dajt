package com.cbelhaffef.dajt.threads;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ThreadInitializer {

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private WatchFolderThread watchFolderThread;

    @PostConstruct
    public void init(){
        taskExecutor.execute(watchFolderThread);
    }

}
