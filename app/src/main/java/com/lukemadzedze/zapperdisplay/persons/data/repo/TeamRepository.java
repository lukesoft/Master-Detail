package com.lukemadzedze.zapperdisplay.persons.data.repo;

import androidx.lifecycle.LiveData;

import com.lukemadzedze.zapperdisplay.persons.data.Resource;
import com.lukemadzedze.zapperdisplay.persons.data.model.Person;
import com.lukemadzedze.zapperdisplay.persons.data.model.Team;
import com.lukemadzedze.zapperdisplay.persons.data.repo.base.Repository;

import java.util.List;

import io.reactivex.Completable;

public interface TeamRepository extends Repository<Team> {
    Completable fetchTeamById(int id);
}
