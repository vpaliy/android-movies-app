package com.popularmovies.vpaliy.data.entity;

import com.google.gson.annotations.SerializedName;

public class MovieEntityDetails {

    @SerializedName("id")
    private int movieId;

    @SerializedName("backdrop_path")
    private String backdropPath;
}
