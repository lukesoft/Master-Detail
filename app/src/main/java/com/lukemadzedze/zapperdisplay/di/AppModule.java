package com.lukemadzedze.zapperdisplay.di;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lukemadzedze.zapperdisplay.persons.PersonsModule;
import com.lukemadzedze.zapperdisplay.persons.data.datasource.local.LocalDatabase;
import com.lukemadzedze.zapperdisplay.utils.MainThreadExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
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
    public LocalDatabase provideDatabase(Application application){
        return LocalDatabase.getInstance(application);
    }

    @Provides
    @Singleton
    @Named("networkExecutor")
    public Executor provideNetworkExecutor(){
        return Executors.newFixedThreadPool(3);
    }

    @Provides
    @Singleton
    @Named("mainThreadExecutor")
    public Executor provideMainThreadExecutor(){
        return new MainThreadExecutor();
    }

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
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .addInterceptor(loggingIntercepter)
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
