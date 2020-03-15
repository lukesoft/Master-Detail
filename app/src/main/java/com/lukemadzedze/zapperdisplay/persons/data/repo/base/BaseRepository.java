package com.lukemadzedze.zapperdisplay.persons.data.repo.base;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.lukemadzedze.zapperdisplay.persons.Status;
import com.lukemadzedze.zapperdisplay.persons.data.Resource;

public abstract class BaseRepository<T> implements Repository<T> {
    protected MutableLiveData<Resource<T>> contentLiveData = new MutableLiveData<>();

    @Override
    public LiveData<Resource<T>> getContentLiveData() {
        return contentLiveData;
    }

    @Nullable
    protected T getCurrentData() {
        T currentData = null;
        if (contentLiveData.getValue() != null) {
            currentData = contentLiveData.getValue().data;
        }

        return currentData;
    }

    @Nullable
    private Status getCurrentStatus() {
        Status currentStatus = null;
        if (contentLiveData.getValue() != null) {
            currentStatus = contentLiveData.getValue().status;
        }
        return currentStatus;
    }


    /**
     * Method changes the data without changing the status
     *
     * @param results
     */

    protected void setResourceData(T results) {
        Status status = getCurrentStatus();
        if (status == null) {
            contentLiveData.postValue(Resource.loading(results));
            return;
        }

        switch (status) {
            case LOADING:
                contentLiveData.postValue(Resource.loading(results));
                break;
            case ERROR:
                contentLiveData.postValue(Resource.error(results));
                break;
            case SUCCESS:
                contentLiveData.postValue(Resource.success(results));
                break;
        }
    }

    /**
     * Method sets the status without changing the data
     *
     * @param status
     */

    protected void setResourceStatus(Status status) {

        switch (status) {
            case ERROR:
                contentLiveData.postValue(Resource.error(getCurrentData()));
                break;
            case LOADING:
                contentLiveData.postValue(Resource.loading(getCurrentData()));
                break;
            case SUCCESS:
                contentLiveData.postValue(Resource.success(getCurrentData()));
                break;
        }
    }
}
