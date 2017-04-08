package com.popularmovies.vpaliy.data.entity;

import com.google.gson.annotations.SerializedName;

public class ReviewEntity {

    @SerializedName("id")
    private int movieId;

    @SerializedName("author")
    private String author;

    @SerializedName("content")
    private String content;

    @SerializedName("url")
    private String url;


    public int getMovieId() {
        return movieId;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }
}
