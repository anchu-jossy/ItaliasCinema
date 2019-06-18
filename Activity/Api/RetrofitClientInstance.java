package com.example.ajit.italiascinema.Activity.Api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {
    private static final Boolean IS_TEST = false;

    private static final String BASE_URL = "http://redex.info/italian_movie/index.php/Api/";


    private static final String TEST_BASE_URL = "http://192.168.1.23/codeigniter-project/italian_movie/index.php/Api/";
    private static Retrofit retrofit;

    private static String getBaseUrl() {
        if (IS_TEST) {
            return TEST_BASE_URL;
        } else {
            return BASE_URL;

        }
    }

    public static Retrofit getRetrofitInstance() {


        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(getBaseUrl())

                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}