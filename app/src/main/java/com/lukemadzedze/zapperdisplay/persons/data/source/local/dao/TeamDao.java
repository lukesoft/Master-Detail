package com.lukemadzedze.zapperdisplay.persons.data.source.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.lukemadzedze.zapperdisplay.persons.data.model.Person;
import com.lukemadzedze.zapperdisplay.persons.data.model.Team;

import java.util.List;

@Dao
public interface TeamDao {
    @Query("SELECT * FROM teams WHERE id=:id LIMIT 1")
    Team getById(int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Long insert(Team team);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    int update(Team team);
}
