package com.example.ajit.italiascinema.Activity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecentAdd {

    @SerializedName("advertisement_id")
    @Expose
    private String advertisementId;
    @SerializedName("start_time")
    @Expose
    private Integer startTime;
    @SerializedName("video_url")
    @Expose
    private String videoUrl;

    public String getAdvertisementId() {
        return advertisementId;
    }

    public void setAdvertisementId(String advertisementId) {
        this.advertisementId = advertisementId;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

}