package com.lukemadzedze.zapperdisplay.di;

import com.lukemadzedze.zapperdisplay.persons.view.activity.MainActivity;
import com.lukemadzedze.zapperdisplay.persons.view.fragment.PersonListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = {FragmentBuilder.class})
    public abstract MainActivity discoverTimelineFragment();
}
