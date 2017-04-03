package com.popularmovies.vpaliy.popularmoviesapp.presentation.mvp.presenter;

import android.support.annotation.NonNull;

import com.popularmovies.vpaliy.popularmoviesapp.presentation.mvp.contract.MovieInfoContract;
import com.popularmovies.vpaliy.popularmoviesapp.presentation.mvp.contract.MovieInfoContract.View;

public class MovieInfoPresenter
        implements MovieInfoContract.Presenter {

    private View view;

    @Override
    public void start(int movieID) {

    }

    @Override
    public void attachView(@NonNull View view) {
        this.view=view;
    }

    @Override
    public void stop() {

    }
}
