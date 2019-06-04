package com.example.ajit.italiascinema.Activity.network;

import com.google.gson.JsonArray;

public interface ArrayResponseCallback {
    void onSuccess(JsonArray jsonObject);
    void onFailure(String error);
}
