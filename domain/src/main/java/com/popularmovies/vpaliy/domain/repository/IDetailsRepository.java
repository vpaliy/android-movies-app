package com.popularmovies.vpaliy.domain.repository;

import rx.Observable;

public interface IDetailsRepository<T> {
    Observable<T> get(String id);
}
