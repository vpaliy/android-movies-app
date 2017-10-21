package com.popularmovies.vpaliy.popularmoviesapp.ui.media;


import android.support.annotation.NonNull;
import com.popularmovies.vpaliy.domain.configuration.SortType;
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope;

@ViewScope
public class MediaPresenter implements MediaContract.Presenter{

    private MediaContract.View view;


    @Override
    public void attachView(@NonNull MediaContract.View view) {

    }

    @Override
    public void start(SortType sortType) {

    }

    @Override
    public void stop() {

    }

    @Override
    public void requestMore(@NonNull SortType sortType) {

    }

    @Override
    public void requestRefresh(@NonNull SortType sortType) {

    }
}
