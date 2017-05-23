package com.popularmovies.vpaliy.data.source.local;

import com.popularmovies.vpaliy.data.entity.MovieDetailEntity;
import com.popularmovies.vpaliy.data.source.DetailsDataSource;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;


@Singleton
public class LocalMovieDetails implements DetailsDataSource<MovieDetailEntity> {

    @Inject
    public LocalMovieDetails(){}

    @Override
    public Observable<MovieDetailEntity> get(int id) {
        return null;
    }

    @Override
    public void insert(MovieDetailEntity item) {

    }
}
