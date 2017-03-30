package com.popularmovies.vpaliy.popularmoviesapp.domain;


import java.util.List;
import rx.Observable;

public interface IRepository<T> {

    Observable<List<T>> getList();
    Observable<T> findById(int ID);

}