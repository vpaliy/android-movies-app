package com.popularmovies.vpaliy.data.entity;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.popularmovies.vpaliy.domain.model.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewEntity {


   // @SerializedName("id")
    private String reviewId;

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

    public static List<Review> convert(List<ReviewEntity> reviewEntities){
        if(reviewEntities==null||reviewEntities.isEmpty()){
            return null;
        }
        List<Review> reviewList=new ArrayList<>(reviewEntities.size());
        for(ReviewEntity reviewEntity:reviewEntities){
            Review review=new Review(0,reviewEntity.getAuthor(),reviewEntity.getContent(),reviewEntity.getUrl());
            reviewList.add(review);
        }

        return reviewList;
    }

    public static List<ReviewEntity> convertBack(List<Review> reviewEntities){
        if(reviewEntities==null||reviewEntities.isEmpty()){
            return null;
        }
        List<ReviewEntity> reviewList=new ArrayList<>(reviewEntities.size());
        for(Review review:reviewEntities){
            ReviewEntity reviewEntity=new ReviewEntity();
            reviewEntity.setAuthor(review.getAuthor());
            reviewEntity.setContent(review.getContent());
            reviewEntity.setUrl(review.getUrl());
            reviewList.add(reviewEntity);
        }

        return reviewList;
    }
}
