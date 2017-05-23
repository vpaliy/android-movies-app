package com.popularmovies.vpaliy.data.source.local;

import com.popularmovies.vpaliy.data.entity.TvShowDetailEntity;
import com.popularmovies.vpaliy.data.source.DetailsDataSource;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class LocalTvShowDetails implements DetailsDataSource<TvShowDetailEntity> {

    @Inject
    public LocalTvShowDetails(){}

    @Override
    public Observable<TvShowDetailEntity> get(int id) {
        return null;
    }

    @Override
    public void insert(TvShowDetailEntity item) {

    }
}
