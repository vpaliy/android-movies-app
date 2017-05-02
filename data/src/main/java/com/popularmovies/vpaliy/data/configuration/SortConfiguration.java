package com.popularmovies.vpaliy.data.configuration;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.popularmovies.vpaliy.domain.configuration.ISortConfiguration.SortType.FAVORITE;
import static com.popularmovies.vpaliy.domain.configuration.ISortConfiguration.SortType.POPULAR;
import static com.popularmovies.vpaliy.domain.configuration.ISortConfiguration.SortType.TOP_RATED;

@Singleton
public class SortConfiguration implements ISortConfiguration {

    private static final String KEY="SORT_TYPE";
    private static final int BY_POPULARITY=0;
    private static final int BY_TOP_RATED=1;
    private static final int BY_FAVORITE=2;


    private final SharedPreferences preferences;
    private SortType sortType;

    @Inject
    public SortConfiguration(@NonNull Context context){
      this.preferences=context.getSharedPreferences
                ("Shared",Context.MODE_PRIVATE);
        this.sortType=getConfiguration();

    }

    @Override
    public void saveConfiguration(@NonNull SortType sortType) {
        this.sortType=sortType;
        switch (sortType){
            case TOP_RATED:
                preferences.edit().putInt(KEY,BY_TOP_RATED).apply();
                break;
            case POPULAR:
                preferences.edit().putInt(KEY,BY_POPULARITY).apply();
                break;
            case FAVORITE:
                preferences.edit().putInt(KEY,BY_FAVORITE).apply();
        }
    }

    @Override
    public SortType getConfiguration() {
        if(sortType!=null){
            return sortType;
        }
        switch (preferences.getInt(KEY,BY_POPULARITY)){
            case BY_TOP_RATED:
                return TOP_RATED;
            case BY_FAVORITE:
                return FAVORITE;
            default:
                return POPULAR;
        }
    }
}