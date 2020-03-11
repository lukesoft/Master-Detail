package com.lukemadzedze.zapperdisplay.persons.data.repo;

import com.lukemadzedze.zapperdisplay.persons.data.model.Person;
import com.lukemadzedze.zapperdisplay.persons.data.source.network.PersonService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class PersonsRepositoryImpl implements PersonsRepository{

    private PersonService service;
    @Inject
    PersonsRepositoryImpl(PersonService service){
        this.service =  service;
    }

    @Override
    public List<Person> getAll() {
        try {
            return this.service.list().execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }
}
