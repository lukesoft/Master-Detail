package com.lukemadzedze.zapperdisplay.persons.data.datasource.network;

import com.lukemadzedze.zapperdisplay.persons.data.response.TeamResponse;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TeamService {
    @POST("person/{Id}")
    Call<TeamResponse> get(@Path("Id") long personId);
}
