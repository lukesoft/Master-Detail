package com.lukemadzedze.zapperdisplay.di;

import android.app.Application;

import com.lukemadzedze.zapperdisplay.App;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Component(modules = {
        AndroidInjectionModule.class,
        AppModule.class})
public interface AppComponent extends AndroidInjector<App> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder create(Application application);
        AppComponent build();
    }
}
