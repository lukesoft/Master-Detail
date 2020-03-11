package com.lukemadzedze.zapperdisplay.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lukemadzedze.zapperdisplay.persons.PersonsModule;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.lukemadzedze.zapperdisplay.AppConfig.BASE_URL;

@Module (includes = PersonsModule.class)
public class AppModule {
    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient client, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(HttpLoggingInterceptor loggingIntercepter) {
        return new OkHttpClient.Builder()
                .addInterceptor(loggingIntercepter)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();


    }

    @Provides
    @Singleton
    public HttpLoggingInterceptor provideLoggingIntercepter() {
        HttpLoggingInterceptor interceptor =  new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }


    @Provides
    @Singleton
    public Gson provideGson() {
        return new GsonBuilder()
                .setLenient()
                .create();

    }
}
