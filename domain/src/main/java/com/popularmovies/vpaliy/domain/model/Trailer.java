package com.popularmovies.vpaliy.domain.model;

public class Trailer {

    private String movieId;
    private String trailerTitle;
    private String trailerUrl;

    public Trailer(String movieId, String trailerUrl, String trailerTitle){
        this.movieId=movieId;
        this.trailerUrl=trailerUrl;
        this.trailerTitle=trailerTitle;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public String getTrailerTitle() {
        return trailerTitle;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    public void setTrailerTitle(String trailerTitle) {
        this.trailerTitle = trailerTitle;
    }
}
