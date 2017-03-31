package com.popularmovies.vpaliy.popularmoviesapp.domain;

import android.support.annotation.NonNull;

public interface ISortConfiguration {


    void saveConfiguration(@NonNull SortType sortType);
    SortType getConfiguration();

    enum SortType{
        POPULAR,
        LATEST
    }
}
