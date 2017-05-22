package com.popularmovies.vpaliy.data.source.local;

import com.popularmovies.vpaliy.data.entity.MovieDetailEntity;
import com.popularmovies.vpaliy.data.source.DetailsDataSource;

import rx.Observable;



public class LocalMovieDetails implements DetailsDataSource<MovieDetailEntity> {

    @Override
    public Observable<MovieDetailEntity> get(int id) {
        return null;
    }

    @Override
    public void insert(MovieDetailEntity item) {

    }
}
