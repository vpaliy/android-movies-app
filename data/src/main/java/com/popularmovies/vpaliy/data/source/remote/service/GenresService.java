package com.popularmovies.vpaliy.data.source.remote.service;

import com.popularmovies.vpaliy.data.source.remote.wrapper.GenreWrapper;
import retrofit2.http.GET;
import rx.Observable;

public interface GenresService {
    @GET("genre/movie/list")
    Observable<GenreWrapper> queryMovieGenres();

    @GET("genre/tv/list")
    Observable<GenreWrapper> queryTvGenres();
}
