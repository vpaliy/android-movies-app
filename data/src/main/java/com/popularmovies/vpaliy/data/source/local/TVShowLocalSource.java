package com.popularmovies.vpaliy.data.source.local;


import android.content.Context;
import android.support.annotation.NonNull;
import com.popularmovies.vpaliy.data.entity.TvShow;
import com.popularmovies.vpaliy.data.entity.TvShowDetailEntity;
import com.popularmovies.vpaliy.data.source.MediaDataSource;
import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class TVShowLocalSource extends MediaDataSource<TvShow,TvShowDetailEntity> {

    @Inject
    public TVShowLocalSource(@NonNull Context context){

    }

    @Override
    public Observable<List<TvShow>> getCovers(@NonNull ISortConfiguration.SortType type) {
        return null;
    }

    @Override
    public Observable<TvShow> getCover(int id) {
        return null;
    }

    @Override
    public Observable<List<TvShow>> requestMoreCovers(@NonNull ISortConfiguration.SortType type) {
        return null;
    }

    @Override
    public boolean isType(int id, ISortConfiguration.SortType sortType) {
        return false;
    }

    @Override
    public void insert(TvShow item, ISortConfiguration.SortType sortType) {

    }

    @Override
    public void insertDetails(TvShowDetailEntity details) {

    }

    @Override
    public Observable<TvShowDetailEntity> getDetails(int id) {
        return null;
    }

    @Override
    public void update(TvShow item, @NonNull ISortConfiguration.SortType sortType) {

    }
}
