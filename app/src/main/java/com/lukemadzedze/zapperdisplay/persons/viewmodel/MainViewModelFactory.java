package com.lukemadzedze.zapperdisplay.persons.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.lukemadzedze.zapperdisplay.persons.data.repo.PersonsRepository;
import com.lukemadzedze.zapperdisplay.persons.data.repo.TeamRepository;
import com.lukemadzedze.zapperdisplay.persons.viewmodel.MainViewModel;

import javax.inject.Inject;

public class MainViewModelFactory implements ViewModelProvider.Factory {
    private final TeamRepository teamRepository;
    private final PersonsRepository personsRepository;

    @Inject
    public MainViewModelFactory(PersonsRepository personsRepository, TeamRepository teamRepository) {
        this.personsRepository = personsRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    @NonNull
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(personsRepository, teamRepository);
        }
        //noinspection unchecked
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
