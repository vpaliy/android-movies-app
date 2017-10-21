package com.popularmovies.vpaliy.data.source.local;

import com.popularmovies.vpaliy.data.entity.SeasonEntity;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class LocalSeasonSource implements DetailsDataSource<SeasonEntity> {

    @Inject
    public LocalSeasonSource(){}

    @Override
    public Observable<SeasonEntity> get(String id) {
        return null;
    }

    @Override
    public void insert(SeasonEntity item) {

    }
}
