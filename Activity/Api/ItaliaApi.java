package com.example.ajit.italiascinema.Activity.Api;

import com.example.ajit.italiascinema.Activity.model.FeatureMovies;
import com.example.ajit.italiascinema.Activity.model.RegisterResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ItaliaApi {

    @POST("user_registration")
    Call<RegisterResponse> registration(
            @Query("phno") String phoneno,
            @Query("username") String username,
            @Query("email") String email,
            @Query("password") String password,
            @Query("gender") String gender);

    @POST("user_login")
    Call<RegisterResponse> login(

            @Query("email") String email,
            @Query("password") String password);

    @POST("movies")
    Call<FeatureMovies>getFeatureMovies();


}





