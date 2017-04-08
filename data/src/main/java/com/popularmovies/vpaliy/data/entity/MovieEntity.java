package com.popularmovies.vpaliy.data.entity;

import com.google.gson.annotations.SerializedName;

import java.sql.Date;
import java.util.List;

/**
 * This is a fake entity
 */
public class MovieEntity {


    private String baseImageUrl="http://image.tmdb.org/t/p/w500/";

    @SerializedName("id")
    private int movieId;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("movie_title")
    private String movieTitle;

    @SerializedName("poster_path")
    private String posterPath;

    private String duration;

    private List<String> genres;

    private List<String> backdrops;

    public int getMovieId() {
        return movieId;
    }

    public int getReleaseYear() {
        return Date.valueOf(releaseDate).getYear();
    }

    public List<String> getGenres() {
        return genres;
    }

    public String getPosterPath() {
        return baseImageUrl+posterPath;
    }

    public List<String> getBackdrops() {
        return backdrops;
    }


    public String getMovieTitle() {
        return movieTitle;
    }

    public String getDuration() {
        return duration;
    }

    public void setBackdrops(List<String> backdrops) {
        this.backdrops = backdrops;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

}
