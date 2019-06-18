package com.example.ajit.italiascinema.Activity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FeatureMoviesResponse  {

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    @SerializedName("count")
    @Expose
    private String count;

    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("info")
    @Expose
    private List<Info> info = null;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Info> getInfo() {
        return info;
    }

    public void setInfo(List<Info> info) {
        this.info = info;
    }
}
