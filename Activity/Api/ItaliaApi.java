package com.ItaliasCinemas.ajit.Italiascinema.Activity.Api;

import com.ItaliasCinemas.ajit.Italiascinema.Activity.model.FeatureMoviesResponse;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.model.ChangePaswordResponse;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.model.ClearHistoryResponse;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.model.EditProfileResponse;

import com.ItaliasCinemas.ajit.Italiascinema.Activity.model.LoginResponse;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.model.RegisterOtpResponse;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.model.RegisterResponse;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.model.SetWatchHistoryResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ItaliaApi {


    @FormUrlEncoded
    @POST("check_otp")
    Call<RegisterOtpResponse> registrationOtp(
            @Field("userid") String userid,
            @Field("otp") String otp);

    @FormUrlEncoded
    @POST("user_registration")
    Call<RegisterResponse> registration(
            @Field("mobilenumber") String phoneno,
            @Field("emailid") String username,
            @Field("username") String email,
            @Field("password") String password,
            @Field("imei_number") String imei);

    @FormUrlEncoded
    @POST("user_login")
    Call<LoginResponse> login(

            @Field("username") String username,
            @Field("password") String password);

    @FormUrlEncoded
    @POST("get_features_data")
    Call<FeatureMoviesResponse> getFeatureMovies(

            @Field("userid") String userid,
            @Field("data") String data);

    @FormUrlEncoded
    @POST("get_trending_data")
    Call<FeatureMoviesResponse> getTrendingData(

            @Field("userid") String userid,
            @Field("data") String data);

    @FormUrlEncoded
    @POST("get_latest_data")
    Call<FeatureMoviesResponse> getLatestData(

            @Field("userid") String userid,
            @Field("data") String data);

    @FormUrlEncoded
    @POST("get_recent_data")
    Call<FeatureMoviesResponse> getRecentData(

            @Field("userid") String userid,
            @Field("data") String data);

    @FormUrlEncoded
    @POST("watch_history")
    Call<SetWatchHistoryResponse> setWatchHistory(

            @Field("userid") String userid,
            @Field("movie_id") String movieid);

    @FormUrlEncoded
    @POST("get_watch_history_data")
    Call<FeatureMoviesResponse> getWatchHistory(

            @Field("userid") String userid);


    @FormUrlEncoded
    @POST("add_favourite")
    Call<FeatureMoviesResponse> setFavourites(

            @Field("userid") String userid,
            @Field("movie_id") String movie_id);


    @FormUrlEncoded
    @POST("get_favourite")
    Call<FeatureMoviesResponse> getFavourites(

            @Field("userid") String userid);


    @FormUrlEncoded
    @POST("get_notification")
    Call<FeatureMoviesResponse> getNotification(

            @Field("userid") String userid);


    @FormUrlEncoded
    @POST("get_allmovie")
    Call<FeatureMoviesResponse> getSearchData(

            @Field("userid") String userid);


    @FormUrlEncoded
    @POST("change_password")
    Call<ChangePaswordResponse> changePassWord(

            @Field("userid") String userid,

            @Field("currentpass") String username,
            @Field("newpass") String password,
            @Field("confirmnewpass") String confirmnewpass);


    @FormUrlEncoded
    @POST("clear_history")
    Call<ClearHistoryResponse> getClearHistory(

            @Field("userid") String userid);


    @FormUrlEncoded
    @POST("edit_profile")
    Call<EditProfileResponse> editProfile(
            @Field("username") String username,
            @Field("emailid") String email,
            @Field("userid") String userid);


    @FormUrlEncoded
    @POST("select_language")
    Call<LoginResponse> getSelectedLanguage(

            @Field("lang_id") String langid,
            @Field("userid") String userid);


    @FormUrlEncoded
    @POST("check_imei")
    Call<RegisterResponse> getImei(

            @Field("imei_number") String imei);

}





