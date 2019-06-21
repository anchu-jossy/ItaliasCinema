package com.ItaliasCinemas.ajit.Italiascinema.Activity.model;

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
    private List<com.ItaliasCinemas.ajit.Italiascinema.Activity.model.Info> info = null;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<com.ItaliasCinemas.ajit.Italiascinema.Activity.model.Info> getInfo() {
        return info;
    }

    public void setInfo(List<com.ItaliasCinemas.ajit.Italiascinema.Activity.model.Info> info) {
        this.info = info;
    }
}
