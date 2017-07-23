package com.popularmovies.vpaliy.data.source.remote.service;

import com.popularmovies.vpaliy.data.entity.CollectionEntity;
import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.entity.TvShow;
import com.popularmovies.vpaliy.data.entity.TvShowInfoEntity;
import com.popularmovies.vpaliy.data.source.remote.wrapper.BackdropsWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.CastWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.MovieWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.ReviewWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.TrailerWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.TvShowsWrapper;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface MoviesService {

    @GET("movie/popular")
    Observable<MovieWrapper> queryPopular(@Query("page") int page);

    @GET("movie/top_rated")
    Observable<MovieWrapper> queryTopRated(@Query("page") int page);

    @GET("movie/latest")
    Observable<MovieWrapper> queryLatest();

    @GET("movie/now_playing")
    Observable<MovieWrapper> queryNowPlaying(@Query("page") int page);

    @GET("movie/upcoming")
    Observable<MovieWrapper> queryUpcoming(@Query("page") int page);

    @GET("movie/{id}")
    Observable<Movie> queryDetails(@Path("id") String id);

    @GET("collection/{collection_id}")
    Observable<CollectionEntity> queryCollection(@Path("collection_id") String id);

    @GET("movie/{id}/images")
    Observable<BackdropsWrapper> queryBackdrops(@Path("id") String id);

    @GET("movie/{id}/reviews")
    Observable<ReviewWrapper> queryReviews(@Path("id") String id);

    @GET("movie/{id}/videos")
    Observable<TrailerWrapper> queryVideos(@Path("id") String id);

    @GET("movie/{id}/similar")
    Observable<MovieWrapper> querySimilarMovies(@Path("id") String id);

    @GET("movie/{movie_id}/recommendations")
    Observable<MovieWrapper> queryRecommendations(@Path("movie_id") String id);

    @GET("movie/{id}/credits")
    Observable<CastWrapper> queryCast(@Path("id") String id);

    @GET("discover/movie")
    Observable<MovieWrapper> discoverMovies(@Query("sort_by") String sortBy, @Query("page") Integer page);
}
