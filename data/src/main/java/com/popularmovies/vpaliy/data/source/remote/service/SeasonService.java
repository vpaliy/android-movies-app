package com.popularmovies.vpaliy.data.source.remote.service;

import com.popularmovies.vpaliy.data.entity.SeasonEntity;
import com.popularmovies.vpaliy.data.source.remote.wrapper.BackdropsWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.CastWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.TrailerWrapper;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface SeasonService {
    @GET("tv/{tv_id}/season/{season_number}")
    Observable<SeasonEntity> querySeason(@Path("tv_id") String id, @Path("season_number") String number);

    @GET("tv/{tv_id}/season/{season_number}/credits")
    Observable<CastWrapper> queryCast(@Path("tv_id") String id, @Path("season_number") String number);

    @GET("tv/{tv_id}/season/{season_number}/images")
    Observable<BackdropsWrapper> queryImages(@Path("tv_id") String id, @Path("season_number") String number);

    @GET("tv/{tv_id}/season/{season_number}/videos")
    Observable<TrailerWrapper> queryVideos(@Path("tv_id") String id, @Path("season_number") String number);
}
