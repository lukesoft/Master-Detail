package com.lukemadzedze.zapperdisplay.utils;

import android.os.Handler;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.lukemadzedze.zapperdisplay.persons.data.model.Person;

import java.sql.Time;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class PollingNetworkBoundResource<ResultType, RequestType> extends NetworkBoundResource<ResultType, RequestType> {
    protected PollingNetworkBoundResource(AppExecutors appExecutors, long delay, long period) {
        this.appExecutors = appExecutors;

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                appExecutors.mainThread().execute(() -> {
                    LiveData<ResultType> dbSource = loadFromDb();
                    fetchFromNetwork(dbSource);
                });
            }
        }, delay, period);

    }

    @Override
    protected boolean shouldFetch(@Nullable ResultType data) {
        return true;
    }
}
