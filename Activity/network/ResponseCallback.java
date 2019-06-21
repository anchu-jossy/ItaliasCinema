package com.ItaliasCinemas.ajit.Italiascinema.Activity.network;

import com.google.gson.JsonObject;

public interface ResponseCallback {
    void onSuccess(JsonObject jsonObject);
    void onFailure(String error);
}