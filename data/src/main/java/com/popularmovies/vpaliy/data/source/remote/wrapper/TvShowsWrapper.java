package com.popularmovies.vpaliy.data.source.remote.wrapper;


import com.popularmovies.vpaliy.data.entity.TvShow;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class TvShowsWrapper {

    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private List<TvShow> tvShows;

    @SerializedName("total_pages")
    private int totalPages;

    public List<TvShow> getTvShows() {
        return tvShows;
    }

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }
}
