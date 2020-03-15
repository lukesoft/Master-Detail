package com.lukemadzedze.zapperdisplay.persons.data.datasource.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.lukemadzedze.zapperdisplay.persons.data.model.Person;

import java.util.List;

@Dao
public interface PersonDao {
    @Query("SELECT * FROM persons")
    LiveData<List<Person>> getPersonsLiveData();

    @Query("SELECT * FROM persons")
    List<Person> getPersons();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Long insert(Person item);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Person> item);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    int update(Person item);

    @Query("DELETE FROM persons")
    void deleteAll();
}
