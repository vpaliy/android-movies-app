package com.popularmovies.vpaliy.data.source;


import android.support.annotation.NonNull;

import com.popularmovies.vpaliy.domain.IRepository;
import com.popularmovies.vpaliy.domain.ISortConfiguration;
public abstract class DataSource<T,D> implements IRepository<T,D> {
    @Override
    public void sort(@NonNull ISortConfiguration.SortType type) {/*Empty method*/}
}
