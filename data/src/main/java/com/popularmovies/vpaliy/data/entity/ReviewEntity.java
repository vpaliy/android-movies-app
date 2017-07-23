package com.popularmovies.vpaliy.data.entity;

import com.google.gson.annotations.SerializedName;
import com.popularmovies.vpaliy.domain.model.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewEntity {

    @SerializedName("id")
    private String reviewId;

    @SerializedName("media_id")
    private String mediaId;

    @SerializedName("author")
    private String author;

    @SerializedName("content")
    private String content;

    @SerializedName("url")
    private String url;

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getUrl() {
        return url;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getReviewId() {
        return reviewId;
    }
}
