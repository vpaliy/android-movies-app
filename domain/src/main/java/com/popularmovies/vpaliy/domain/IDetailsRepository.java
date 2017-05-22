package com.popularmovies.vpaliy.domain;

import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration.SortType;

import rx.Observable;

public interface IDetailsRepository<T> {
    Observable<T> get(int id);
    void update(T item);
    boolean isType(SortType sortType);
}
