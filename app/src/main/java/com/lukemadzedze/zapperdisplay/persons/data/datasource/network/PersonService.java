package com.lukemadzedze.zapperdisplay.persons.data.datasource.network;

import com.lukemadzedze.zapperdisplay.persons.data.response.PersonsResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PersonService {
    @GET("persons")
    Call<PersonsResponse> list();
}
