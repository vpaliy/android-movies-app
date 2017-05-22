package com.popularmovies.vpaliy.data.source;

import com.popularmovies.vpaliy.domain.repository.ICoverRepository;
import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration.SortType;

public interface CoverDataSource<T> extends ICoverRepository<T> {
    void insert(T item, SortType sortType);
}
