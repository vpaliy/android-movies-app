package com.popularmovies.vpaliy.data.entity;


import java.util.List;

public class MovieDetailEntity implements HasId{

    private Movie movie;
    private List<ReviewEntity> reviews;
    private List<TrailerEntity> trailers;
    private List<ActorEntity> cast;
    private List<Movie> similarMovies;
    private List<BackdropImage> backdropImages;
    private List<Genre> genres;
    private CollectionEntity collectionEntity;

    @Override
    public int id() {
        return movie.id();
    }


    public void setCollectionEntity(CollectionEntity collectionEntity) {
        this.collectionEntity = collectionEntity;
    }

    public CollectionEntity getCollectionEntity() {
        return collectionEntity;
    }

    public int getMovieId() {
        return movie.getMovieId();
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

    public List<Movie> getSimilarMovies() {
        return similarMovies;
    }

    public Movie getMovie() {
        return movie;
    }

    public List<BackdropImage> getBackdropImages() {
        return backdropImages;
    }

    public void setCast(List<ActorEntity> cast) {
        this.cast = cast;
    }

    public void setReviews(List<ReviewEntity> reviews) {
        this.reviews = reviews;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setFavorite(boolean isFavorite){
        if(movie!=null) movie.setFavorite(isFavorite);
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
