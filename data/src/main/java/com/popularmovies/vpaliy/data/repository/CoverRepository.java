package com.popularmovies.vpaliy.data.repository;


import android.content.Context;
import android.support.annotation.NonNull;

import com.popularmovies.vpaliy.data.mapper.Mapper;
import com.popularmovies.vpaliy.domain.ICoverRepository;
import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration;
import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration.SortType;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * A repository that provides covers for movies, tv shows and people.
 * @param <T>
 * @param <F>
 */

public class CoverRepository<T,F> extends AbstractRepository<T>
        implements ICoverRepository<T>{

    private final Mapper<T,F> coverMapper;

    @Inject
    public CoverRepository(@NonNull Context context,
                           @NonNull Mapper<T, F> coverMapper){
        super(context);
        this.coverMapper=coverMapper;
    }

    @Override
    public Observable<List<T>> get(SortType type) {
        return null;
    }

    @Override
    public Observable<T> get(int id) {
        return null;
    }

    @Override
    public void update(T item,SortType type) {

    }

    @Override
    public Observable<List<T>> requestMore(SortType type) {
        return null;
    }

}
