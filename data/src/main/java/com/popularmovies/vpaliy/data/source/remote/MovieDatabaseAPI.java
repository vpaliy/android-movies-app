package com.popularmovies.vpaliy.data.source.remote;


import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.source.remote.wrapper.BackdropsWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.CastWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.MovieWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.ReviewWrapper;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

interface MovieDatabaseAPI {

    @GET("movie/popular")
    Observable<MovieWrapper> getPopularMovies(@Query("page") int page);

    @GET("movie/topRated")
    Observable<MovieWrapper> getTopRatedMovies(@Query("page") int page);

    @GET("movie/{id}")
    Observable<Movie> getMovieDetails(@Path("id") String id);

    @GET("movie/{id}/images")
    Observable<BackdropsWrapper> getBackdrops(@Path("id") String id);

    @GET("movie/{id}/reviews")
    Observable<ReviewWrapper> getReviews(@Path("id") String id);

    @GET("movie/{id}/similar")
    Observable<MovieWrapper> getSimilarMovies(@Path("id") String id);

    @GET("movie/{id}/credits")
    Observable<CastWrapper> getMovieCast(@Path("id") String id);

    @GET("discover/movie")
    Observable<MovieWrapper> discoverMovies(@Query("sort_by") String sortBy, @Query("page") Integer page);

}
