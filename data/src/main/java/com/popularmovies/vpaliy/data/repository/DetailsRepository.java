package com.popularmovies.vpaliy.data.repository;


import android.content.Context;
import android.support.annotation.NonNull;

import com.popularmovies.vpaliy.data.mapper.Mapper;
import com.popularmovies.vpaliy.domain.IDetailsRepository;
import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration.SortType;

import javax.inject.Inject;

import rx.Observable;

public class DetailsRepository<T,F> extends AbstractRepository<T>
        implements IDetailsRepository<T> {

    private final Mapper<T,F> detailsMapper;

    @Inject
    public DetailsRepository(@NonNull Context context,
                             @NonNull Mapper<T,F> detailsMapper){
        super(context);
        this.detailsMapper=detailsMapper;
    }

    @Override
    public Observable<T> get(int id) {
        return null;
    }

    @Override
    public void update(T item) {

    }

    @Override
    public boolean isType(SortType sortType) {
        return false;
    }
}
