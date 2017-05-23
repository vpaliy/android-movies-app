package com.popularmovies.vpaliy.data.source.local;

import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.source.CoverDataSource;
import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class LocalMovieCovers implements CoverDataSource<Movie> {

    @Inject
    public LocalMovieCovers(){}

    @Override
    public Observable<List<Movie>> get(ISortConfiguration.SortType type) {
        return null;
    }

    @Override
    public Observable<Movie> get(int id) {
        return null;
    }

    @Override
    public void insert(Movie item, ISortConfiguration.SortType sortType) {

    }

    @Override
    public void update(Movie item, ISortConfiguration.SortType type) {

    }

    @Override
    public Observable<List<Movie>> requestMore(ISortConfiguration.SortType type) {
        return null;
    }
}
