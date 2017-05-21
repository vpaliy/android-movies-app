package com.popularmovies.vpaliy.data.source.remote;


import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.entity.TvShow;
import com.popularmovies.vpaliy.data.entity.TvShowInfoEntity;
import com.popularmovies.vpaliy.data.mapper.ActorMapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.BackdropsWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.CastWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.MovieWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.ReviewWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.TrailerWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.TvShowsWrapper;
import rx.Observable;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieDatabaseAPI {

    @GET("movie/popular")
    Observable<MovieWrapper> getPopularMovies(@Query("page") int page);

    @GET("movie/top_rated")
    Observable<MovieWrapper> getTopRatedMovies(@Query("page") int page);

    @GET("movie/latest")
    Observable<MovieWrapper> getLatestMovies();

    @GET("movie/now_playing")
    Observable<MovieWrapper> getNowPlayingMovies(@Query("page") int page);

    @GET("movie/upcoming")
    Observable<MovieWrapper> getUpcomingMovies(@Query("page") int page);

    @GET("movie/{id}")
    Observable<Movie> getMovieDetails(@Path("id") String id);

    @GET("movie/{id}/images")
    Observable<BackdropsWrapper> getBackdrops(@Path("id") String id);

    @GET("movie/{id}/reviews")
    Observable<ReviewWrapper> getReviews(@Path("id") String id);

    @GET("movie/{id}/videos")
    Observable<TrailerWrapper> getVideos(@Path("id") String id);

    @GET("movie/{id}/similar")
    Observable<MovieWrapper> getSimilarMovies(@Path("id") String id);

    @GET("movie/{id}/credits")
    Observable<CastWrapper> getMovieCast(@Path("id") String id);

    @GET("tv/latest")
    Observable<TvShowsWrapper> getLatestTv(@Query("page") int page);

    @GET("tv/airing_today")
    Observable<TvShowsWrapper> getUpcomingTv(@Query("page") int page);

    @GET("tv/on_the_air")
    Observable<TvShowsWrapper> getNowPlayingTv(@Query("page") int page);

    @GET("tv/popular")
    Observable<TvShowsWrapper> getPopularTv(@Query("page") int page);

    @GET("tv/top_rated")
    Observable<TvShowsWrapper> getTopRatedTv(@Query("page") int page);

    @GET("tv/{tv_id}")
    Observable<TvShow> getTvShow(@Path("id") int id);

    @GET("tv/{tv_id}")
    Observable<TvShowInfoEntity> getTvShowDetails(@Path("id") int id);

    @GET("tv/{tv_id}/images")
    Observable<BackdropsWrapper> getBackdropsForTvShow(@Path("id") int id);

    @GET("tv/{tv_id}/credits")
    Observable<CastWrapper> getTvShowCast(@Path("id") int id);

    @GET("tv/{tv_id}/season/{season_number}")
    Observable<TvShow> getTvSeason(@Path("id") int tvId, @Path("season_number") int seasonNumber);

    @GET("discover/movie")
    Observable<MovieWrapper> discoverMovies(@Query("sort_by") String sortBy, @Query("page") Integer page);

}
