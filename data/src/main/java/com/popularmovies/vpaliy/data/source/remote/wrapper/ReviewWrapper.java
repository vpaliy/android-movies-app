package com.popularmovies.vpaliy.data.source.remote.wrapper;


import com.google.gson.annotations.SerializedName;
import com.popularmovies.vpaliy.domain.model.Review;

import java.util.List;

public class ReviewWrapper {

    @SerializedName("results")
    private List<Review> reviewList;

    public List<Review> getReviewList() {
        return reviewList;
    }
}
