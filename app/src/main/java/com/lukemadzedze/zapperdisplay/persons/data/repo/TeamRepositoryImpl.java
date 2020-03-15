package com.lukemadzedze.zapperdisplay.persons.data.repo;


import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.lukemadzedze.zapperdisplay.persons.Status;
import com.lukemadzedze.zapperdisplay.persons.data.Resource;
import com.lukemadzedze.zapperdisplay.persons.data.model.Team;
import com.lukemadzedze.zapperdisplay.persons.data.repo.base.BaseRepository;
import com.lukemadzedze.zapperdisplay.persons.data.source.local.dao.TeamDao;
import com.lukemadzedze.zapperdisplay.persons.data.source.network.TeamService;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.functions.Action;
import retrofit2.Response;

public class TeamRepositoryImpl extends BaseRepository<Team> implements TeamRepository {

    final private TeamDao dao;
    final private TeamService service;
    private int personId;

    @Inject
    public TeamRepositoryImpl(TeamDao dao, TeamService service) {
        this.dao = dao;
        this.service = service;
    }

    @Override
    public Completable fetchTeamById(final int personId) {
        this.personId = personId;

        return Completable.fromAction(new Action() {
            @Override
            public void run() {
                setResourceStatus(Status.LOADING);

                loadTeamFromLocal(personId);
                getTeamFromRemote(personId);

            }
        });
    }

    private void addTeamToDB(Team newData) {
        newData.setId(personId);

        if (getCurrentData() != null && getCurrentData().equals(newData)) {
            return;
        }

        Long result = dao.insert(newData);
        if (result == -1) {
            dao.update(newData);
        }

        loadTeamFromLocal(this.personId);
    }


    private void loadTeamFromLocal(int person_id) {
        Team results = dao.getById(person_id);
        if ((results != null)) {
            setResourceData(results);
        }
    }

    private void getTeamFromRemote(int person_id) {
        try {
            Response<Team> response = service.get(person_id).execute();
            if ((response.isSuccessful()) && (response.body() != null)) {
                setResourceStatus(Status.SUCCESS);
                addTeamToDB(response.body());
            } else {
                setResourceStatus(Status.ERROR);
                Log.d("TeamRepositoryImpl", "something went wrong server side" + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();

            setResourceStatus(Status.ERROR);
        }
    }

}
