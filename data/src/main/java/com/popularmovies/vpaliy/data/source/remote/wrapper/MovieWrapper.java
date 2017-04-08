package com.popularmovies.vpaliy.data.source.remote.wrapper;

import com.google.gson.annotations.SerializedName;
import com.popularmovies.vpaliy.data.entity.Movie;

import java.util.List;

public class MovieWrapper {

    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private List<Movie> movies;

    public List<Movie> getCoverList() {
        return movies;
    }
}
