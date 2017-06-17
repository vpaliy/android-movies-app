package com.popularmovies.vpaliy.data.source.remote.wrapper;

import com.google.gson.annotations.SerializedName;
import com.popularmovies.vpaliy.data.entity.Movie;

import java.util.List;

public class MovieWrapper {

    @SerializedName("page")
    private int page;

    @SerializedName("results")
    public List<Movie> movies;

    @SerializedName("total_pages")
    private int totalPages;

    public List<Movie> getCoverList() {
        return movies;
    }

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }
}
