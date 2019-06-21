package com.ItaliasCinemas.ajit.Italiascinema.Activity.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Add implements Parcelable {

    @SerializedName("advertisement_id")
    @Expose
    private String advertisementId;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("video_url")
    @Expose
    private String videoUrl;

    public String getAdvertisementId() {
        return advertisementId;
    }

    public void setAdvertisementId(String advertisementId) {
        this.advertisementId = advertisementId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.advertisementId);
        dest.writeString(this.startTime);
        dest.writeString(this.videoUrl);
    }

    public Add() {
    }

    protected Add(Parcel in) {
        this.advertisementId = in.readString();
        this.startTime = in.readString();
        this.videoUrl = in.readString();
    }

    public static final Parcelable.Creator<Add> CREATOR = new Parcelable.Creator<Add>() {
        @Override
        public Add createFromParcel(Parcel source) {
            return new Add(source);
        }

        @Override
        public Add[] newArray(int size) {
            return new Add[size];
        }
    };
}
