package com.popularmovies.vpaliy.domain.configuration;

import android.support.annotation.NonNull;

public interface ISortConfiguration {


    void saveConfiguration(@NonNull SortType sortType);
    SortType getConfiguration();

}
