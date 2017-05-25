package com.popularmovies.vpaliy.data.source.local;

import com.popularmovies.vpaliy.data.entity.TvShow;
import com.popularmovies.vpaliy.data.source.CoverDataSource;
import com.popularmovies.vpaliy.domain.configuration.SortType;

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
    public Observable<List<TvShow>> requestMore(SortType type) {
        return null;
    }

    @Override
    public Observable<List<TvShow>> get(SortType type) {
        return null;
    }

    @Override
    public void update(TvShow item, SortType type) {

    }

    @Override
    public void insert(TvShow item, SortType sortType) {

    }
}
