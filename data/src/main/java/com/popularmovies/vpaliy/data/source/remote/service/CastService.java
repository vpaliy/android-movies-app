package com.popularmovies.vpaliy.data.source.remote.service;


import com.popularmovies.vpaliy.data.entity.ActorDetailEntity;
import com.popularmovies.vpaliy.data.entity.BackdropImage;
import com.popularmovies.vpaliy.data.source.remote.wrapper.CastImagesWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.MovieWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.TaggedImagesWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.TvShowsWrapper;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface CastService {

    @GET("person/{person_id}")
    Observable<ActorDetailEntity> getActor(@Path("person_id") int id);

    @GET("person/{person_id}/movie_credits")
    Observable<MovieWrapper> queryMovieCredits(@Path("person_id") int id);

    @GET("person/{person_id}/tv_credits")
    Observable<TvShowsWrapper> queryTvShowCredits(@Path("person_id") int id);

    @GET("person/{person_id}/images")
    Observable<CastImagesWrapper> queryImages(@Path("person_id") int id);

    @GET("person/{person_id}/tagged_images")
    Observable<TaggedImagesWrapper> queryTaggedImages(@Path("person_id") int id);
}
