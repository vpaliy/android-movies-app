package com.popularmovies.vpaliy.data.source.local;

import com.popularmovies.vpaliy.data.entity.ActorDetailEntity;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class LocalActorDetails implements DetailsDataSource<ActorDetailEntity> {

    @Inject
    public LocalActorDetails(){}

    @Override
    public void insert(ActorDetailEntity item) {
    }

    @Override
    public Observable<ActorDetailEntity> get(String id) {
        return Observable.empty();
    }
}
