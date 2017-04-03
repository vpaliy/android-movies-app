package com.popularmovies.vpaliy.domain.model;

import java.util.List;

public class MovieCover {

    private int movieId;
    private int releaseYear;
    private String movieTitle;
    private String posterPath;
    private String duration;
    private List<String> genres;
    private List<String> backdrops;

    public int getMovieId() {
        return movieId;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public List<String> getGenres() {
        return genres;
    }

    public String getPosterPath() {
        return posterPath;
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

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }
}
