package com.lukemadzedze.zapperdisplay.persons.data.repo;

import android.util.Log;

import com.lukemadzedze.zapperdisplay.persons.Status;
import com.lukemadzedze.zapperdisplay.persons.data.model.Person;
import com.lukemadzedze.zapperdisplay.persons.data.model.PersonListResponse;
import com.lukemadzedze.zapperdisplay.persons.data.repo.base.BaseRepository;
import com.lukemadzedze.zapperdisplay.persons.data.source.local.dao.PersonDao;
import com.lukemadzedze.zapperdisplay.persons.data.source.network.PersonService;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.functions.Action;
import retrofit2.Response;


public class PersonsRepositoryImpl extends BaseRepository<List<Person>> implements PersonsRepository {
    private static final String TAG = "PersonsRepositoryImpl";

    final private PersonDao dao;
    final private PersonService service;

    @Inject
    public PersonsRepositoryImpl(PersonService service, PersonDao dao) {
        this.service = service;
        this.dao = dao;
    }

    @Override
    public Completable fetchData() {
        return Completable.fromAction(new Action() {
            @Override
            public void run() {
                setResourceStatus(Status.LOADING);

                loadPersonsFromLocal();
                getPersonsFromRemote();

            }
        });
    }

    private void loadPersonsFromLocal() {
        List<Person> results = dao.getAll();
        if ((results != null) && results.size() > 0) {
            setResourceData(results);
        }
    }

    private void getPersonsFromRemote() {
        try {
            Log.d(TAG, "getPersonsFromRemote");
            Response<PersonListResponse> response = service.list().execute();
            if ((response.isSuccessful()) && (response.body() != null)) {
                setResourceStatus(Status.SUCCESS);
                saveRemotePersonsDataToLocal(response.body().getPersons());
            } else {
                setResourceStatus(Status.ERROR);
                Log.d(TAG, "something went wrong server side" + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "IOException");
            setResourceStatus(Status.ERROR);
        }
    }

    private void saveRemotePersonsDataToLocal(List<Person> personList) {
        if (getCurrentData() != null && getCurrentData().equals(personList)) {
            return;
        }

        dao.deleteAll();
        dao.insertAll(personList);

        loadPersonsFromLocal();
    }
}
