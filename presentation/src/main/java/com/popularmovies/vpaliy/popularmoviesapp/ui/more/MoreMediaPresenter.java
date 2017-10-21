package com.popularmovies.vpaliy.popularmoviesapp.ui.more;


import android.support.annotation.NonNull;

import com.popularmovies.vpaliy.domain.configuration.SortType;
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope;

@ViewScope
public class MoreMediaPresenter implements MoreMediaContract.Presenter{

    @Override
    public void attachView(@NonNull MoreMediaContract.View view) {

    }

    @Override
    public void start(SortType sortType) {

    }

    @Override
    public void stop() {

    }

    @Override
    public void requestRefresh(@NonNull SortType sortType) {

    }

    @Override
    public void requestMore(@NonNull SortType sortType) {

    }
}
