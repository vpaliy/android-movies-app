package com.popularmovies.vpaliy.data.source.remote;

import android.support.annotation.NonNull;

import com.popularmovies.vpaliy.data.entity.MovieDetailEntity;
import com.popularmovies.vpaliy.data.entity.MovieEntity;
import com.popularmovies.vpaliy.data.source.DataSource;
import com.popularmovies.vpaliy.domain.ISortConfiguration;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class RemoteSource extends DataSource<MovieEntity,MovieDetailEntity> {

    private ISortConfiguration sortConfiguration;

    @Inject
    public RemoteSource(@NonNull ISortConfiguration sortConfiguration){
        this.sortConfiguration=sortConfiguration;
    }

    @Override
    public Observable<List<MovieEntity>> getCovers() {
        return null;
    }

    @Override
    public Observable<MovieDetailEntity> getDetails(int ID) {
        return null;
    }

    @Override
    public Observable<MovieEntity> getCover(int ID) {
        return null;
    }
}
