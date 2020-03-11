package com.lukemadzedze.zapperdisplay.persons.data.source.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Query;

import com.lukemadzedze.zapperdisplay.persons.data.model.Person;

@Dao
public interface PersonDao {
    @Query("SELECT * FROM persons")
    public LiveData<Person> getAll();
}
