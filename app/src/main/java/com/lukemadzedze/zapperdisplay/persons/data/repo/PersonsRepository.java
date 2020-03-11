package com.lukemadzedze.zapperdisplay.persons.data.repo;

import com.lukemadzedze.zapperdisplay.persons.data.model.Person;

import java.util.List;

public interface PersonsRepository {
    List<Person> getAll();
}
