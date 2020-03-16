package com.lukemadzedze.zapperdisplay.utils;

import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Named;

public class AppExecutors {
    private final Executor networkIO;
    private final Executor mainThread;

    @Inject
    public AppExecutors(@Named("networkExecutor") Executor networkIO, @Named("mainThreadExecutor") Executor mainThread) {
        this.networkIO = networkIO;
        this.mainThread = mainThread;
    }

    public Executor networkIO() {
        return networkIO;
    }

    public Executor mainThread() {
        return mainThread;
    }
}