package com.popularmovies.vpaliy.data.source.local;

import com.popularmovies.vpaliy.data.entity.TvShowDetailEntity;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class LocalTvShowDetails implements DetailsDataSource<TvShowDetailEntity> {

    @Inject
    public LocalTvShowDetails(){}

    @Override
    public Observable<TvShowDetailEntity> get(String id) {
        return null;
    }

    @Override
    public void insert(TvShowDetailEntity item) {

    }
}
