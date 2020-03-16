package com.lukemadzedze.zapperdisplay.utils;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class NetworkBoundResource<ResultType, RequestType> {
    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();
    AppExecutors appExecutors;

    @MainThread
    protected NetworkBoundResource(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;

        result.setValue(Resource.loading(null));
        LiveData<ResultType> dbSource = loadFromDb();
        result.addSource(dbSource, data -> {
            result.removeSource(dbSource);
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource);
            } else {
                result.addSource(dbSource, newData -> setValue(Resource.success(newData)));
            }
        });
    }

    NetworkBoundResource() {
    }

    @MainThread
    private void setValue(Resource<ResultType> newValue) {
        if (!Objects.equals(result.getValue(), newValue)) {
            result.setValue(newValue);
        }
    }

    void fetchFromNetwork(final LiveData<ResultType> dbSource) {
        result.addSource(dbSource, newData -> setValue(Resource.loading(newData)));
        createCall().enqueue(new Callback<RequestType>() {
            @Override
            public void onResponse(Call<RequestType> call, Response<RequestType> response) {
                result.removeSource(dbSource);
                if (response.body() != null) {
                    saveResultAndReInit(response.body());
                } else {
                    result.addSource(dbSource, newData -> setValue(Resource.error("Null response from the server", newData)));
                }

            }

            @Override
            public void onFailure(Call<RequestType> call, Throwable t) {
                onFetchFailed();
                result.removeSource(dbSource);
                result.addSource(dbSource, newData -> setValue(Resource.error(t.getMessage(), newData)));
            }
        });
    }

    @MainThread
    private void saveResultAndReInit(RequestType response) {
        this.appExecutors.networkIO().execute(() -> {
            saveCallResult(response);
            this.appExecutors.mainThread().execute(() -> result.addSource(loadFromDb(), newData -> setValue(Resource.success(newData))));
        });
    }

    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    @MainThread
    protected abstract boolean shouldFetch(@Nullable ResultType data);

    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    @NonNull
    @MainThread
    protected abstract Call<RequestType> createCall();

    @MainThread
    private void onFetchFailed() {
    }

    public final LiveData<Resource<ResultType>> getAsLiveData() {
        return result;
    }
}
