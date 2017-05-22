package com.popularmovies.vpaliy.data.source.local;

import com.popularmovies.vpaliy.data.entity.TvShowDetailEntity;
import com.popularmovies.vpaliy.data.source.DetailsDataSource;

import rx.Observable;


public class LocalTvShowDetails implements DetailsDataSource<TvShowDetailEntity> {

    @Override
    public Observable<TvShowDetailEntity> get(int id) {
        return null;
    }

    @Override
    public void insert(TvShowDetailEntity item) {

    }
}
