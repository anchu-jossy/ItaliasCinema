package com.ItaliasCinemas.ajit.Italiascinema.Activity.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.ItaliasCinemas.ajit.Italiascinema.Activity.model.Add;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Info implements Parcelable {

    public String getReleaseStatus() {
        return releaseStatus;
    }

    public void setReleaseStatus(String releaseStatus) {
        this.releaseStatus = releaseStatus;
    }

    @SerializedName("release_status")
    @Expose
    private String releaseStatus;
    @SerializedName("movie_id")
    @Expose
    private String movieId;
    @SerializedName("movie_name")
    @Expose
    private String movieName;
    @SerializedName("star_cast")
    @Expose
    private String starCast;
    @SerializedName("director")
    @Expose
    private String director;
    @SerializedName("genre")
    @Expose
    private String genre;
    @SerializedName("details")
    @Expose
    private String details;
    @SerializedName("duration_no")
    @Expose
    private int durationNo;
    @SerializedName("duration_time")
    @Expose
    private String durationTime;
    @SerializedName("thumbnails")
    @Expose
    private String thumbnails;
    @SerializedName("subtitle")
    @Expose
    private String subtitle;
    @SerializedName("video_link")
    @Expose
    private String videoLink;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("adds")
    @Expose
    private List<Add> adds = null;

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getStarCast() {
        return starCast;
    }

    public void setStarCast(String starCast) {
        this.starCast = starCast;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getDurationNo() {
        return durationNo;
    }

    public void setDurationNo(int durationNo) {
        this.durationNo = durationNo;
    }

    public String getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(String durationTime) {
        this.durationTime = durationTime;
    }

    public String getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(String thumbnails) {
        this.thumbnails = thumbnails;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public List<Add> getAdds() {
        return adds;
    }

    public void setAdds(List<Add> adds) {
        this.adds = adds;
    }


    public Info() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.releaseStatus);
        dest.writeString(this.movieId);
        dest.writeString(this.movieName);
        dest.writeString(this.starCast);
        dest.writeString(this.director);
        dest.writeString(this.genre);
        dest.writeString(this.details);
        dest.writeInt(this.durationNo);
        dest.writeString(this.durationTime);
        dest.writeString(this.thumbnails);
        dest.writeString(this.subtitle);
        dest.writeString(this.videoLink);
        dest.writeString(this.date);
        dest.writeString(this.rating);
        dest.writeTypedList(this.adds);
    }

    protected Info(Parcel in) {
        this.releaseStatus = in.readString();
        this.movieId = in.readString();
        this.movieName = in.readString();
        this.starCast = in.readString();
        this.director = in.readString();
        this.genre = in.readString();
        this.details = in.readString();

        this.durationTime = in.readString();
        this.thumbnails = in.readString();
        this.subtitle = in.readString();
        this.videoLink = in.readString();
        this.date = in.readString();
        this.rating = in.readString();
        this.adds = in.createTypedArrayList(Add.CREATOR);
    }

    public static final Parcelable.Creator<Info> CREATOR = new Parcelable.Creator<Info>() {
        @Override
        public Info createFromParcel(Parcel source) {
            return new Info(source);
        }

        @Override
        public Info[] newArray(int size) {
            return new Info[size];
        }
    };
}