package com.popularmovies.vpaliy.popularmoviesapp.presentation.mvp.contract;

import android.support.annotation.NonNull;

import com.popularmovies.vpaliy.popularmoviesapp.domain.model.Movie;
import com.popularmovies.vpaliy.popularmoviesapp.presentation.mvp.BasePresenter;
import com.popularmovies.vpaliy.popularmoviesapp.presentation.mvp.BaseView;

public interface DetailsMovieContract {

    interface View extends BaseView<Presenter> {
        void attachPresenter(@NonNull Presenter presenter);
        void showDetails(@NonNull Movie movie);
    }

    interface Presenter extends BasePresenter<View> {
        void attachView(@NonNull View view);
        void start(int ID);
        void stop();
    }
}
