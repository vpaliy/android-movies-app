package com.popularmovies.vpaliy.data.configuration;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.popularmovies.vpaliy.domain.configuration.ISortConfiguration.SortType.FAVORITE;
import static com.popularmovies.vpaliy.domain.configuration.ISortConfiguration.SortType.LATEST;
import static com.popularmovies.vpaliy.domain.configuration.ISortConfiguration.SortType.MUST_WATCH;
import static com.popularmovies.vpaliy.domain.configuration.ISortConfiguration.SortType.NOW_PLAYING;
import static com.popularmovies.vpaliy.domain.configuration.ISortConfiguration.SortType.POPULAR;
import static com.popularmovies.vpaliy.domain.configuration.ISortConfiguration.SortType.TOP_RATED;
import static com.popularmovies.vpaliy.domain.configuration.ISortConfiguration.SortType.UPCOMING;
import static com.popularmovies.vpaliy.domain.configuration.ISortConfiguration.SortType.WATCHED;

@Singleton
public class SortConfiguration implements ISortConfiguration {

    private static final String KEY="SORT_TYPE";

    private static final int BY_POPULARITY=0;
    private static final int BY_TOP_RATED=1;
    private static final int BY_FAVORITE=2;
    private static final int BY_LATEST=3;
    private static final int BY_NOW_PLAYING=4;
    private static final int BY_UPCOMING=5;
    private static final int BY_WATCHED=6;
    private static final int BY_MUST_WATCH=7;


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
            case MUST_WATCH:
                preferences.edit().putInt(KEY,BY_MUST_WATCH).apply();
            case WATCHED:
                preferences.edit().putInt(KEY,BY_WATCHED).apply();
            case UPCOMING:
                preferences.edit().putInt(KEY,BY_UPCOMING).apply();
            case NOW_PLAYING:
                preferences.edit().putInt(KEY,BY_NOW_PLAYING).apply();
            case LATEST:
                preferences.edit().putInt(KEY,BY_LATEST).apply();
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
            case BY_LATEST:
                return LATEST;
            case BY_MUST_WATCH:
                return MUST_WATCH;
            case BY_NOW_PLAYING:
                return NOW_PLAYING;
            case BY_WATCHED:
                return WATCHED;
            case BY_UPCOMING:
                return UPCOMING;
            default:
                return POPULAR;
        }
    }
}