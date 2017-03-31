package com.popularmovies.vpaliy.popularmoviesapp.data.source;


import android.support.annotation.NonNull;

import com.popularmovies.vpaliy.popularmoviesapp.domain.IRepository;
import com.popularmovies.vpaliy.popularmoviesapp.domain.ISortConfiguration;

public abstract class DataSource<T> implements IRepository<T> {
    @Override
    public void sort(@NonNull ISortConfiguration.SortType type) {/*Empty method*/}
}
