package com.popularmovies.vpaliy.data.source.remote.wrapper;

import com.google.gson.annotations.SerializedName;
import com.popularmovies.vpaliy.data.entity.TrailerEntity;

import java.util.List;

public class TrailerWrapper {

    @SerializedName("results")
    private List<TrailerEntity> trailers;

    public List<TrailerEntity> getTrailers() {
        return trailers;
    }
}
