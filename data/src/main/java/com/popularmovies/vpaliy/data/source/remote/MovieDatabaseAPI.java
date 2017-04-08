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

public interface MovieDatabaseAPI {

    @GET("movie/popular")
    Observable<MovieWrapper> getPopularMovies(@Query("api_key") String apiKey);

    @GET("/movie/{id}")
    Observable<Movie> getMovieDetails(@Query("api_key") String apiKey,
                                      @Path("id") int movieId);

    @GET("/movie/{id}/images")
    Observable<BackdropsWrapper> getBackdrops(@Query("api_key") String apiKey,
                                               @Path("id") int movieId);
    @GET("/movie/{id}/reviews")
    Observable<ReviewWrapper> getReviews(@Query("api_key") String apiKey,
                                         @Path("id") int movieId);

    @GET("/movie/{id}/similar")
    Observable<MovieWrapper> getSimilarMovies(@Query("api_key") String apiKey,
                                              @Path("id") int movieId);

    @GET("/movie/{id}/credits")
    Observable<CastWrapper> getMovieCast(@Query("api_key") String apiKey,
                                    @Path("id") int movieId);

}
