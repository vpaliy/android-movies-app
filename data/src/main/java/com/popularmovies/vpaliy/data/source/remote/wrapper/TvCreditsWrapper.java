package com.popularmovies.vpaliy.data.source.remote.wrapper;

import com.google.gson.annotations.SerializedName;
import com.popularmovies.vpaliy.data.entity.TvShow;

import java.util.List;

public class TvCreditsWrapper {

    @SerializedName("cast")
    public List<TvShow> credits;
}
