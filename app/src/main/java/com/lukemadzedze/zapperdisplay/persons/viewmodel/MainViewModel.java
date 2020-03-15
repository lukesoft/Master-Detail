package com.lukemadzedze.zapperdisplay.persons.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.lukemadzedze.zapperdisplay.utils.Resource;
import com.lukemadzedze.zapperdisplay.persons.data.model.Person;
import com.lukemadzedze.zapperdisplay.persons.data.model.Team;
import com.lukemadzedze.zapperdisplay.persons.data.repo.PersonsRepository;
import com.lukemadzedze.zapperdisplay.persons.data.repo.TeamRepository;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class MainViewModel extends ViewModel {
    private String TAG = "MainViewModel";
    private final CompositeDisposable disposables = new CompositeDisposable();

    private PersonsRepository personsRepository;
    private TeamRepository teamRepository;

    public MainViewModel(PersonsRepository personsRepository, TeamRepository teamRepository) {
        this.personsRepository = personsRepository;
        this.teamRepository = teamRepository;
    }

    public LiveData<Resource<Team>> getTeamByPersonId(int personId) {
        return teamRepository.getTeamByPersonId(personId);
    }

    public LiveData<Resource<List<Person>>> getPersons() {
        return personsRepository.getPersons();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.dispose();
    }
}

