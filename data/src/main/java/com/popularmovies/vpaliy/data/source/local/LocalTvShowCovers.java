package com.popularmovies.vpaliy.data.source.local;

import com.popularmovies.vpaliy.data.entity.TvShow;
import com.popularmovies.vpaliy.data.source.CoverDataSource;
import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class LocalTvShowCovers implements CoverDataSource<TvShow> {

    @Inject
    public LocalTvShowCovers(){}

    @Override
    public Observable<TvShow> get(int id) {
        return null;
    }

    @Override
    public Observable<List<TvShow>> requestMore(ISortConfiguration.SortType type) {
        return null;
    }

    @Override
    public Observable<List<TvShow>> get(ISortConfiguration.SortType type) {
        return null;
    }

    @Override
    public void update(TvShow item, ISortConfiguration.SortType type) {

    }

    @Override
    public void insert(TvShow item, ISortConfiguration.SortType sortType) {

    }
}
