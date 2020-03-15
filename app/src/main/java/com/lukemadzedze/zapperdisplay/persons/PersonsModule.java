package com.lukemadzedze.zapperdisplay.persons;


import com.lukemadzedze.zapperdisplay.persons.data.repo.PersonsRepository;
import com.lukemadzedze.zapperdisplay.persons.data.repo.impl.PersonsRepositoryImpl;
import com.lukemadzedze.zapperdisplay.persons.data.repo.TeamRepository;
import com.lukemadzedze.zapperdisplay.persons.data.repo.impl.TeamRepositoryImpl;
import com.lukemadzedze.zapperdisplay.persons.data.datasource.local.LocalDatabase;
import com.lukemadzedze.zapperdisplay.persons.data.datasource.local.dao.PersonDao;
import com.lukemadzedze.zapperdisplay.persons.data.datasource.local.dao.TeamDao;
import com.lukemadzedze.zapperdisplay.persons.data.datasource.network.PersonService;
import com.lukemadzedze.zapperdisplay.persons.data.datasource.network.TeamService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class PersonsModule {
    @Provides
    @Singleton
    public PersonService providePersonService(Retrofit retrofit) {
        return retrofit.create(PersonService.class);
    }

    @Provides
    @Singleton
    public TeamService provideTeamService(Retrofit retrofit) {
        return retrofit.create(TeamService.class);
    }

    @Provides
    @Singleton
    public PersonDao providePersonDao(LocalDatabase db) {
        return db.getPersonDao();
    }


    @Provides
    @Singleton
    public TeamDao provideTeamDao(LocalDatabase db) {
        return db.getTeamDao();
    }


    @Provides
    @Singleton
    public PersonsRepository providePersonsRepo(PersonsRepositoryImpl repo) {
        return repo;
    }


    @Provides
    @Singleton
    public TeamRepository provideTeamRepo(TeamRepositoryImpl repo) {
        return repo;
    }
}

