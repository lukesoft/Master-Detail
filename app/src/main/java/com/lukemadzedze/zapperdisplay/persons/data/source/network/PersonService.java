package com.lukemadzedze.zapperdisplay.persons.data.source.network;

import com.lukemadzedze.zapperdisplay.persons.data.model.Person;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface PersonService {
    @GET("persons/")
    Call<List<Person>> list();
}
