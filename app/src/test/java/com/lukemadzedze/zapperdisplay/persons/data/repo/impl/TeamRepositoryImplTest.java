package com.lukemadzedze.zapperdisplay.persons.data.repo.impl;

import android.os.Handler;

import com.lukemadzedze.zapperdisplay.persons.data.datasource.local.dao.TeamDao;
import com.lukemadzedze.zapperdisplay.persons.data.datasource.network.TeamService;
import com.lukemadzedze.zapperdisplay.persons.data.repo.TeamRepository;

import org.junit.Before;
import org.mockito.Mock;

import java.util.concurrent.Executor;

import static org.junit.Assert.*;

public class TeamRepositoryImplTest {

    @Mock
    TeamDao teamCache;
    @Mock
    TeamService teamRemote;
    @Mock
    Executor IOexecutor;
    @Mock
    Handler UIExecutor;

    TeamRepository repository;
    @Before
    public void setUp() throws Exception {
//        Executor ex =  Executors.
//        repository= TeamRepository(teamCache, teamRemote)
    }
}