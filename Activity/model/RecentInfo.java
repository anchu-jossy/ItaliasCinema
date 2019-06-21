package com.ItaliasCinemas.ajit.Italiascinema.Activity.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecentInfo {

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
    private List<RecentAdd> adds = null;

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

    public List<RecentAdd> getAdds() {
        return adds;
    }

    public void setAdds(List<RecentAdd> adds) {
        this.adds = adds;
    }

}