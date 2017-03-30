package com.popularmovies.vpaliy.popularmoviesapp.presentation.mvp.contract;

import android.support.annotation.NonNull;

import com.popularmovies.vpaliy.popularmoviesapp.domain.model.Movie;
import com.popularmovies.vpaliy.popularmoviesapp.presentation.mvp.BasePresenter;
import com.popularmovies.vpaliy.popularmoviesapp.presentation.mvp.BaseView;

import java.util.List;

public interface MoviesContract {

    interface View extends BaseView<Presenter> {
        void attachPresenter(@NonNull Presenter presenter);
        void showMovies(@NonNull List<Movie> movies);
        void setLoadingIndicator(boolean isLoading);
        void showErrorMessage();
        void showEmptyMessage();
    }

    interface Presenter extends BasePresenter<View> {
        void attachView(@NonNull View view);
        void requestDataRefresh();
        void start();
        void stop();

    }
}
