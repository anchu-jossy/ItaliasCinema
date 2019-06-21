package com.ItaliasCinemas.ajit.Italiascinema.Activity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecentResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("info")
    @Expose
    private List<RecentInfo> info = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<RecentInfo> getInfo() {
        return info;
    }

    public void setInfo(List<RecentInfo> info) {
        this.info = info;
    }

}

