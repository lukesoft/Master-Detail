package com.lukemadzedze.zapperdisplay.di;

import com.lukemadzedze.zapperdisplay.persons.view.fragment.PersonDetailFragment;
import com.lukemadzedze.zapperdisplay.persons.view.fragment.PersonListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuilder {
    @ContributesAndroidInjector
    public abstract PersonListFragment discoverTimelineFragment();

    @ContributesAndroidInjector
    public abstract PersonDetailFragment personDetailFragment();
}
