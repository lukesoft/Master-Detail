package com.lukemadzedze.zapperdisplay.persons.data.datasource.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.lukemadzedze.zapperdisplay.persons.data.model.Team;

@Dao
public interface TeamDao {
    @Query("SELECT * FROM teams WHERE id=:id LIMIT 1")
    Team getById(int id);

    @Query("SELECT * FROM teams WHERE id=:id LIMIT 1")
    LiveData<Team> getByIdLiveData(int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Long insert(Team team);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    int update(Team team);
}
