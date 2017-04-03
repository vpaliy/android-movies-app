package com.popularmovies.vpaliy.data.entity;

public class TrailerEntity {

    private int movieId;
    private String trailerTitle;
    private String trailerUrl;

    public TrailerEntity(int movieId, String trailerUrl, String trailerTitle){
        this.movieId=movieId;
        this.trailerUrl=trailerUrl;
        this.trailerTitle=trailerTitle;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public String getTrailerTitle() {
        return trailerTitle;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    public void setTrailerTitle(String trailerTitle) {
        this.trailerTitle = trailerTitle;
    }
}
