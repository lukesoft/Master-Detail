package com.lukemadzedze.zapperdisplay.persons.data.repo;

import androidx.lifecycle.LiveData;

import com.lukemadzedze.zapperdisplay.persons.data.model.Team;
import com.lukemadzedze.zapperdisplay.utils.Resource;

import java.util.List;

import io.reactivex.Completable;

public interface TeamRepository {
    LiveData<Resource<Team>> getTeamByPersonId(int id);
}
