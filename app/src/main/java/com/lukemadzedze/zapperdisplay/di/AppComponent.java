package com.lukemadzedze.zapperdisplay.di;

import android.app.Application;

import com.lukemadzedze.zapperdisplay.App;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AppModule.class,
        ActivityBuilder.class})
public interface AppComponent extends AndroidInjector<App> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder create(Application application);
        AppComponent build();
    }
}
