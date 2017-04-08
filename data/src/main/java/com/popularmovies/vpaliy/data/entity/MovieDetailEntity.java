package com.popularmovies.vpaliy.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieDetailEntity {

    @SerializedName("id")
    private int movieId;

    private Movie movie;

    private MovieInfoEntity movieInfo;

    private MovieEntity movieCover;
    private List<ReviewEntity> reviews;
    private List<TrailerEntity> trailers;
    private List<ActorEntity> cast;
    private List<Movie> similarMovies;
    private List<BackdropImage> backdropImages;

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

    public List<Movie> getSimilarMovies() {
        return similarMovies;
    }

    public Movie getMovie() {
        return movie;
    }

    public List<BackdropImage> getBackdropImages() {
        return backdropImages;
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

    public void setSimilarMovies(List<Movie> similarMovies) {
        this.similarMovies = similarMovies;
    }

    public void setBackdropImages(List<BackdropImage> backdropImages) {
        this.backdropImages = backdropImages;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
