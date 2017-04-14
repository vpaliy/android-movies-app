package com.popularmovies.vpaliy.data.source.remote.wrapper;


import com.google.gson.annotations.SerializedName;
import com.popularmovies.vpaliy.data.entity.ReviewEntity;

import java.util.List;

public class ReviewWrapper {

    @SerializedName("results")
    private List<ReviewEntity> reviewList;

    public List<ReviewEntity> getReviewList() {
        return reviewList;
    }
}
