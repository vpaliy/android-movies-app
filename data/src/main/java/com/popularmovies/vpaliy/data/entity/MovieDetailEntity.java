package com.popularmovies.vpaliy.data.entity;

import java.util.List;

public class MovieDetailEntity {

    private int movieId;
    private MovieInfoEntity movieInfo;
    private MovieEntity movieCover;
    private List<ReviewEntity> reviews;
    private List<TrailerEntity> trailers;
    private List<ActorEntity> cast;
    private List<MovieEntity> similarMovies;

    public int getMovieId() {
        return movieId;
    }

    public List<ActorEntity> getCast() {
        return cast;
    }

    public List<ReviewEntity> getReviews() {
        return reviews;
    }

    public List<TrailerEntity> getTrailers() {
        return trailers;
    }

    public MovieEntity getMovieCover() {
        return movieCover;
    }

    public MovieInfoEntity getMovieInfo() {
        return movieInfo;
    }

    public List<MovieEntity> getSimilarMovies() {
        return similarMovies;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setCast(List<ActorEntity> cast) {
        this.cast = cast;
    }

    public void setMovieCover(MovieEntity movieCover) {
        this.movieCover = movieCover;
    }

    public void setMovieInfo(MovieInfoEntity movieInfo) {
        this.movieInfo = movieInfo;
    }

    public void setReviews(List<ReviewEntity> reviews) {
        this.reviews = reviews;
    }

    public void setTrailers(List<TrailerEntity> trailers) {
        this.trailers = trailers;
    }

    public void setSimilarMovies(List<MovieEntity> similarMovies) {
        this.similarMovies = similarMovies;
    }
}
