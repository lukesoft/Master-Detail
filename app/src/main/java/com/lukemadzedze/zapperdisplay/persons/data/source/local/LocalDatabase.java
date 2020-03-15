package com.lukemadzedze.zapperdisplay.persons.data.source.local;

import android.content.Context;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.lukemadzedze.zapperdisplay.persons.data.model.Person;
import com.lukemadzedze.zapperdisplay.persons.data.model.Team;
import com.lukemadzedze.zapperdisplay.persons.data.source.local.dao.PersonDao;
import com.lukemadzedze.zapperdisplay.persons.data.source.local.dao.TeamDao;

@Database(entities = {Person.class, Team.class},
        version = 1, exportSchema = false)
public abstract class LocalDatabase extends RoomDatabase {
    public abstract PersonDao getPersonDao();
    public abstract TeamDao getTeamDao();

    private static LocalDatabase INSTANCE;
    public static LocalDatabase getInstance(final Context context){
        if(INSTANCE == null){
            synchronized (LocalDatabase.class){
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            LocalDatabase.class,"local_cache.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
