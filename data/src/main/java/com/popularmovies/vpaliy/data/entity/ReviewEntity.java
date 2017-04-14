package com.popularmovies.vpaliy.data.entity;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.popularmovies.vpaliy.domain.model.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewEntity {


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
}
