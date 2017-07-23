package com.popularmovies.vpaliy.domain.model;


import java.util.List;

public class MovieDetails {

    private String movieId;
    private MovieInfo movieInfo;
    private MediaCover movieCover;
    private List<Review> reviews;
    private List<Trailer> trailers;
    private List<ActorCover> cast;
    private List<MediaCover> similarMovies;
    private List<MediaCover> recommended;
    private MediaCollection collection;

    public MovieDetails(String movieId){
        this.movieId=movieId;
    }

    public void setCollection(MediaCollection collection) {
        this.collection = collection;
    }

    public void setRecommended(List<MediaCover> recommended) {
        this.recommended = recommended;
    }

    public List<MediaCover> getRecommended() {
        return recommended;
    }

    public String getMovieId() {
        return movieId;
    }

    public List<ActorCover> getCast() {
        return cast;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public List<Trailer> getTrailers() {
        return trailers;
    }

    public MediaCover getMovieCover() {
        return movieCover;
    }

    public MovieInfo getMovieInfo() {
        return movieInfo;
    }

    public MediaCollection getCollection() {
        return collection;
    }

    public List<MediaCover> getSimilarMovies() {
        return similarMovies;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public void setCast(List<ActorCover> cast) {
        this.cast = cast;
    }

    public void setMovieCover(MediaCover movieCover) {
        this.movieCover = movieCover;
    }

    public void setMovieInfo(MovieInfo movieInfo) {
        this.movieInfo = movieInfo;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
    }

    public void setSimilarMovies(List<MediaCover> similarMovies) {
        this.similarMovies = similarMovies;
    }
}
