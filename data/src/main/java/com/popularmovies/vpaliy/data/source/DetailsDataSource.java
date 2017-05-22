package com.popularmovies.vpaliy.data.source;

import com.popularmovies.vpaliy.domain.repository.IDetailsRepository;

public interface DetailsDataSource<T> extends IDetailsRepository<T> {
    void insert(T item);
}
