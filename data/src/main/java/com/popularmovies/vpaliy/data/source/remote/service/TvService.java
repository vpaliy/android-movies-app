package com.popularmovies.vpaliy.data.source.remote.service;

import com.popularmovies.vpaliy.data.entity.TvShow;
import com.popularmovies.vpaliy.data.entity.TvShowInfoEntity;
import com.popularmovies.vpaliy.data.source.remote.wrapper.BackdropsWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.CastWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.TrailerWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.TvShowsWrapper;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface TvService {

    @GET("tv/latest")
    Observable<TvShowsWrapper> queryLatest(@Query("page") int page);

    @GET("tv/airing_today")
    Observable<TvShowsWrapper> queryAiringToday(@Query("page") int page);

    @GET("tv/on_the_air")
    Observable<TvShowsWrapper> queryOnAir(@Query("page") int page);

    @GET("tv/popular")
    Observable<TvShowsWrapper> queryPopular(@Query("page") int page);

    @GET("tv/top_rated")
    Observable<TvShowsWrapper> queryTopRated(@Query("page") int page);

    @GET("tv/{tv_id}")
    Observable<TvShow> queryTvShow(@Path("tv_id") String id);

    @GET("tv/{tv_id}/videos")
    Observable<TrailerWrapper> queryVideos(@Path("tv_id") String id);

    @GET("tv/{tv_id}")
    Observable<TvShowInfoEntity> queryTvShowInfo(@Path("tv_id") String id);

    @GET("tv/{tv_id}/images")
    Observable<BackdropsWrapper> queryBackdrops(@Path("tv_id") String id);

    @GET("tv/{tv_id}/credits")
    Observable<CastWrapper> queryCast(@Path("tv_id") String id);

    @GET("tv/{tv_id}/season/{season_number}")
    Observable<TvShow> querySeason(@Path("tv_id") int tvId, @Path("season_number") int seasonNumber);

    @GET("tv/{tv_id}/similar")
    Observable<TvShowsWrapper> querySimilar(@Path("tv_id") String id);

}
