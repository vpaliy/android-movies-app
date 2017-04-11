package com.popularmovies.vpaliy.data.entity;


import com.google.gson.annotations.SerializedName;
import com.popularmovies.vpaliy.data.Constants;

import java.util.Comparator;
import java.util.List;

public class Movie {

    private static final String baseImageUrl="http://image.tmdb.org/t/p/w780/";

    @SerializedName("id")
    private int movieId;

    @SerializedName("genres")
    private List<Genre> genres;

    @SerializedName("backdrop_path")
    private String backdrop_path;

    private List<BackdropImage> backdropImages;

    @SerializedName("homepage")
    private String homepage;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("original_title")
    private String originalTitle;

    @SerializedName("overview")
    private String overview;

    @SerializedName("popularity")
    private Number popularity;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("revenue")
    private long revenue;

    @SerializedName("budget")
    private long budget;

    @SerializedName("runtime")
    private int runtime;

    @SerializedName("status")
    private String status;

    @SerializedName("title")
    private String title;

    @SerializedName("video")
    private boolean video;

    @SerializedName("vote_average")
    private Number voteAverage;

    @SerializedName("vote_count")
    private long voteCount;


    public int getMovieId() {
        return movieId;
    }

    public double getPopularity(){
        return popularity!=null?popularity.doubleValue():0;
    }

    public long getRevenue() {
        return revenue;
    }

    public int getRuntime() {
        return runtime;
    }


    public List<Genre> getGenres() {
        return genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public List<BackdropImage> getBackdropImages() {
        return backdropImages;
    }

    public void setBackdropImages(List<BackdropImage> backdropImages) {
        this.backdropImages = backdropImages;

    }

    public String getBackdrop_path() {
        return Constants.BASE_MOVIE_URL+Constants.IMAGE_SIZE_W780+ backdrop_path;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public long getVoteCount() {
        return voteCount;
    }

    public double getVoteAverage() {
        return voteAverage!=null?voteAverage.doubleValue():0d;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return Constants.BASE_MOVIE_URL+ Constants.IMAGE_SIZE_W185+posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public long getBudget() {
        return budget;
    }
}
