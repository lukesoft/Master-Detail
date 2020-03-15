package com.lukemadzedze.zapperdisplay.persons.data.source.network;

import com.lukemadzedze.zapperdisplay.persons.data.model.Person;
import com.lukemadzedze.zapperdisplay.persons.data.model.PersonListResponse;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface PersonService {
    @GET("persons")
    Call<PersonListResponse> list();
}
