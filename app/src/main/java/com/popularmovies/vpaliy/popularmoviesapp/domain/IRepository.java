package com.popularmovies.vpaliy.popularmoviesapp.domain;


import android.support.annotation.NonNull;

import java.util.List;
import rx.Observable;
import static com.popularmovies.vpaliy.popularmoviesapp.domain.ISortConfiguration.SortType;

public interface IRepository<T> {

    Observable<List<T>> getList();
    Observable<T> findById(int ID);

    void sort(@NonNull SortType type);

}