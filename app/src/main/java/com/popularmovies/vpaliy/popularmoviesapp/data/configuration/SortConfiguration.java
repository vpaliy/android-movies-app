package com.popularmovies.vpaliy.popularmoviesapp.data.configuration;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import javax.inject.Inject;

import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.domain.ISortConfiguration;

public class SortConfiguration implements ISortConfiguration {

    private static final String KEY="SORT_TYPE";
    private static final int BY_POPULARITY=0;
    private static final int BY_LATEST=1;

    private final SharedPreferences preferences;

    @Inject
    public SortConfiguration(@NonNull Context context){
        this.preferences=context.getSharedPreferences
                (context.getString(R.string.appConfigurations),Context.MODE_PRIVATE);


    }

    @Override
    public void saveConfiguration(@NonNull SortType sortType) {
        switch (sortType){
            case LATEST:
                preferences.edit().putInt(KEY,BY_LATEST).apply();
                break;
            case POPULAR:
                preferences.edit().putInt(KEY,BY_POPULARITY).apply();
                break;
        }
    }

    @Override
    public SortType getConfiguration() {
        switch (preferences.getInt(KEY,BY_LATEST)){
            case BY_POPULARITY:
                return SortType.POPULAR;
            default:
                return SortType.LATEST;
        }
    }
}
