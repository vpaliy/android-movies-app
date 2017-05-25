package com.popularmovies.vpaliy.domain.repository;

import com.popularmovies.vpaliy.domain.configuration.SortType;
import java.util.List;
import rx.Observable;


public interface ICoverRepository<T> {

    Observable<List<T>> get(SortType type);
    Observable<T> get(int id);
    Observable<List<T>> requestMore(SortType type);
    void update(T item, SortType type);
}
