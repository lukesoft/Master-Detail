package com.lukemadzedze.zapperdisplay.persons.data.repo.impl;

import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.lukemadzedze.zapperdisplay.persons.data.model.Person;
import com.lukemadzedze.zapperdisplay.persons.data.repo.PersonsRepository;
import com.lukemadzedze.zapperdisplay.persons.data.response.PersonsResponse;
import com.lukemadzedze.zapperdisplay.persons.data.datasource.local.dao.PersonDao;
import com.lukemadzedze.zapperdisplay.persons.data.datasource.network.PersonService;
import com.lukemadzedze.zapperdisplay.utils.PollingNetworkBoundResource;
import com.lukemadzedze.zapperdisplay.utils.Resource;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import retrofit2.Call;


public class PersonsRepositoryImpl implements PersonsRepository {
    private static final String TAG = "PersonsRepositoryImpl";
    private static final int DELAY = 0;
    private static final int PERIOD = 1000 * 60;

    private PersonDao dao;
    private PersonService service;
    private Executor IOexecutor;
    private Handler UIExecutor;


    @Inject
    public PersonsRepositoryImpl(PersonService service, PersonDao dao, Executor IOexecutor, Handler UIExecutor) {
        this.service = service;
        this.dao = dao;
        this.IOexecutor = IOexecutor;
        this.UIExecutor = UIExecutor;
    }

    @Override
    public LiveData<Resource<List<Person>>> getPersons() {
        return new PollingNetworkBoundResource<List<Person>, PersonsResponse>(this.IOexecutor, this.UIExecutor, DELAY, PERIOD) {
            @Override
            protected void saveCallResult(@NonNull PersonsResponse response) {
                List<Person> cachedList = dao.getPersons();
                if (cachedList != null && cachedList.equals(response.getPersons())) {
                    return;
                }

                dao.deleteAll();
                dao.insertAll(response.getPersons());
            }

            @NonNull
            @Override
            protected LiveData<List<Person>> loadFromDb() {
                return dao.getPersonsLiveData();
            }

            @NonNull
            @Override
            protected Call<PersonsResponse> createCall() {
                return service.list();
            }
        }.getAsLiveData();
    }

}
