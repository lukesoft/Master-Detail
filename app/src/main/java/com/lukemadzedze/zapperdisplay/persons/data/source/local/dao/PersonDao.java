package com.lukemadzedze.zapperdisplay.persons.data.source.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.lukemadzedze.zapperdisplay.persons.data.model.Person;

import java.util.List;

@Dao
public interface PersonDao {
    @Query("SELECT * FROM persons")
    List<Person> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Long insert(Person item);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Person> item);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    int update(Person item);

    @Query("DELETE FROM persons")
    void deleteAll();
}
