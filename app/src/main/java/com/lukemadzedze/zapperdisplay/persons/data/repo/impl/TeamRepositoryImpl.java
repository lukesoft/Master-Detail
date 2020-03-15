package com.lukemadzedze.zapperdisplay.persons.data.repo.impl;


import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.lukemadzedze.zapperdisplay.persons.data.repo.TeamRepository;
import com.lukemadzedze.zapperdisplay.persons.data.response.TeamResponse;
import com.lukemadzedze.zapperdisplay.utils.NetworkBoundResource;
import com.lukemadzedze.zapperdisplay.utils.Resource;
import com.lukemadzedze.zapperdisplay.persons.data.model.Team;
import com.lukemadzedze.zapperdisplay.persons.data.datasource.local.dao.TeamDao;
import com.lukemadzedze.zapperdisplay.persons.data.datasource.network.TeamService;

import java.util.concurrent.Executor;

import javax.inject.Inject;

import retrofit2.Call;

public class TeamRepositoryImpl implements TeamRepository {
    private TeamDao teamCache;
    private TeamService teamRemote;
    private Executor IOexecutor;
    private Handler UIExecutor;

    @Inject
    public TeamRepositoryImpl(TeamDao teamCache, TeamService teamRemote, Executor IOexecutor, Handler UIExecutor) {
        this.teamCache = teamCache;
        this.teamRemote = teamRemote;
        this.IOexecutor = IOexecutor;
        this.UIExecutor = UIExecutor;
    }

    @Override
    public LiveData<Resource<Team>> getTeamByPersonId(int personId) {
        return new NetworkBoundResource<Team, TeamResponse>(this.IOexecutor, this.UIExecutor) {
            @Override
            protected void saveCallResult(@NonNull TeamResponse teamResponse) {
                teamResponse.setId(personId);

                Team cachedTeam = teamCache.getById(personId);
                if (cachedTeam != null && cachedTeam.equals(teamResponse)) {
                    return;
                }

                Long result = teamCache.insert(teamResponse);
                if (result == -1) {
                    teamCache.update(teamResponse);
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable Team data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<Team> loadFromDb() {
                return teamCache.getByIdLiveData(personId);
            }

            @NonNull
            @Override
            protected Call<TeamResponse> createCall() {
                return teamRemote.get(personId);
            }
        }.getAsLiveData();
    }
}
