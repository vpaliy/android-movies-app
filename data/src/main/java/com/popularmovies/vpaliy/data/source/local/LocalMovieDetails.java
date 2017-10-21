package com.popularmovies.vpaliy.data.source.local;

import com.popularmovies.vpaliy.data.entity.MovieDetailEntity;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;


@Singleton
public class LocalMovieDetails implements DetailsDataSource<MovieDetailEntity> {

    @Inject
    public LocalMovieDetails(){}

    @Override
    public Observable<MovieDetailEntity> get(String id) {
        return null;
    }

    @Override
    public void insert(MovieDetailEntity item) {

    }
}
