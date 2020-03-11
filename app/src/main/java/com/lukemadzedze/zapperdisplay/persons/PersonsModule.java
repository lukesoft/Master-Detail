package com.lukemadzedze.zapperdisplay.persons;



import com.lukemadzedze.zapperdisplay.persons.data.source.network.PersonService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class PersonsModule {
    @Provides
    @Singleton
    public PersonService providePersonService(Retrofit retrofit) {
        return retrofit.create(PersonService.class);
    }
}

