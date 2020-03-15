package com.lukemadzedze.zapperdisplay.persons.data.repo;

import androidx.lifecycle.LiveData;

import com.lukemadzedze.zapperdisplay.persons.data.model.Person;
import com.lukemadzedze.zapperdisplay.utils.Resource;

import java.util.List;

public interface PersonsRepository {
    LiveData<Resource<List<Person>>> getPersons();
}
