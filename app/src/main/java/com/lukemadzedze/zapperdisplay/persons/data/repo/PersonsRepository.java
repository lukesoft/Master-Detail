package com.lukemadzedze.zapperdisplay.persons.data.repo;
import com.lukemadzedze.zapperdisplay.persons.data.model.Person;
import com.lukemadzedze.zapperdisplay.persons.data.repo.base.Repository;

import java.util.List;

import io.reactivex.Completable;

public interface PersonsRepository extends Repository<List<Person>> {
    Completable fetchData();
}
