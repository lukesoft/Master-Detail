package com.lukemadzedze.zapperdisplay.persons.data.source.network;

import com.lukemadzedze.zapperdisplay.persons.data.model.PersonListResponse;
import com.lukemadzedze.zapperdisplay.persons.data.model.Team;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TeamService {
    @POST("person/{Id}")
    Call<Team> get(@Path("Id") long personId);
}
