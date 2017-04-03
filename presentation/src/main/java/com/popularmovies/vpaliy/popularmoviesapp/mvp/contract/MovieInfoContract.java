package com.popularmovies.vpaliy.popularmoviesapp.mvp.contract;


import android.support.annotation.NonNull;

import com.popularmovies.vpaliy.domain.model.MovieInfo;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.BasePresenter;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.BaseView;

public interface MovieInfoContract {

    interface View extends BaseView<Presenter> {
        void attachPresenter(@NonNull Presenter presenter);
        void showInfo(@NonNull MovieInfo movieInfo);
        void showTrailer();
    }

    interface Presenter extends BasePresenter<View> {
        void attachView(@NonNull View view);
        void start(int movieID);
        void stop();

    }
}
