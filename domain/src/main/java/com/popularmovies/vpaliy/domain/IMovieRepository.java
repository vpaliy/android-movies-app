package com.popularmovies.vpaliy.domain;

import android.support.annotation.NonNull;

import rx.Observable;

public interface IMovieRepository<T,D> extends IRepository<T,D> {
    Observable<T> requestMoreCovers();
    Observable<T> sortBy(@NonNull ISortConfiguration.SortType type);
}
