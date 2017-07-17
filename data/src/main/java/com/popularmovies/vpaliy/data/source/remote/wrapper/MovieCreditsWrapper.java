package com.popularmovies.vpaliy.data.source.remote.wrapper;

import com.google.gson.annotations.SerializedName;
import com.popularmovies.vpaliy.data.entity.Movie;

import java.util.List;

public class MovieCreditsWrapper {

    @SerializedName("cast")
    public List<Movie> credits;
}
