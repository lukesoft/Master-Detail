package com.lukemadzedze.zapperdisplay.persons.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.lukemadzedze.zapperdisplay.persons.data.model.Person;
import com.lukemadzedze.zapperdisplay.persons.data.model.Team;
import com.lukemadzedze.zapperdisplay.persons.data.repo.PersonsRepository;
import com.lukemadzedze.zapperdisplay.persons.data.repo.TeamRepository;
import com.lukemadzedze.zapperdisplay.utils.Resource;

import java.util.List;

public class MainViewModel extends ViewModel {
    private PersonsRepository personsRepository;
    private TeamRepository teamRepository;
    private int selectedPersonId;

    public MainViewModel(PersonsRepository personsRepository, TeamRepository teamRepository) {
        this.personsRepository = personsRepository;
        this.teamRepository = teamRepository;
        this.selectedPersonId = 0;
    }

    public void setSelectedPersonId(int pendingPersonId) {
        this.selectedPersonId = pendingPersonId;
    }

    public int getSelectedPersonId() {
        return this.selectedPersonId;
    }

    public LiveData<Resource<Team>> getTeamByPersonId() {
        return teamRepository.getTeamByPersonId(this.selectedPersonId);
    }

    public LiveData<Resource<List<Person>>> getPersons() {
        return personsRepository.getPersons();
    }
}

