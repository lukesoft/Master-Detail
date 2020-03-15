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
    protected PollingNetworkBoundResource(Executor mExecutor, Handler UIExecutor, long delay, long period) {
        this.executor = mExecutor;
        this.UIExecutor = UIExecutor;

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                UIExecutor.post(() -> {
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
