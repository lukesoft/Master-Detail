package com.lukemadzedze.zapperdisplay.persons.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.lukemadzedze.zapperdisplay.persons.data.Resource;
import com.lukemadzedze.zapperdisplay.persons.data.model.Person;
import com.lukemadzedze.zapperdisplay.persons.data.model.Team;
import com.lukemadzedze.zapperdisplay.persons.data.repo.PersonsRepository;
import com.lukemadzedze.zapperdisplay.persons.data.repo.TeamRepository;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ViewModel {
    private String TAG = "MainViewModel";

    private static final Long DELAY_IN_SECONDS = 60L;
    private static final Long START_NOW_IN_SECONDS = 0L;

    private final CompositeDisposable disposables = new CompositeDisposable();

    private PersonsRepository personsRepository;
    private TeamRepository teamRepository;

    public MainViewModel(PersonsRepository personsRepository, TeamRepository teamRepository) {
        this.personsRepository = personsRepository;
        this.teamRepository = teamRepository;
    }


    public void fetchTeamById(int id) {
        CompletableObserver completableObserver = teamRepository.fetchTeamById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe");
                        disposables.add(d);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, e.getMessage());
                    }
                });
    }

    public void fetchPersons() {
        CompletableObserver completableObserver =
                Observable.interval(START_NOW_IN_SECONDS, DELAY_IN_SECONDS, TimeUnit.SECONDS)
                        .flatMapCompletable(new Function<Long, CompletableSource>() {
                            @Override
                            public CompletableSource apply(@NonNull Long aLong) {
                                return personsRepository.fetchData();
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new CompletableObserver() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                disposables.add(d);
                                Log.d(TAG, "onSubscribe");
                            }

                            @Override
                            public void onComplete() {
                                Log.d(TAG, "onComplete");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d(TAG, "onError");
                            }
                        });
    }

    public LiveData<Resource<Team>> teamLiveData() {
        return teamRepository.getContentLiveData();
    }

    public LiveData<Resource<List<Person>>> personsLiveData() {
        return personsRepository.getContentLiveData();
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.dispose();
    }
}

