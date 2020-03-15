package com.lukemadzedze.zapperdisplay.persons.data.repo.base;

import androidx.lifecycle.LiveData;

import com.lukemadzedze.zapperdisplay.persons.data.Resource;
import com.lukemadzedze.zapperdisplay.persons.data.model.Team;

import io.reactivex.Completable;

public interface Repository<E> {
    LiveData<Resource<E>> getContentLiveData();
}
