package com.popularmovies.vpaliy.popularmoviesapp.mvp.contract;

import android.support.annotation.NonNull;

import com.popularmovies.vpaliy.domain.ISortConfiguration;
import com.popularmovies.vpaliy.domain.model.MovieCover;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.BasePresenter;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.BaseView;

import java.util.List;

public interface MoviesContract {

    interface View extends BaseView<Presenter> {
        void attachPresenter(@NonNull Presenter presenter);
        void showMovies(@NonNull List<MovieCover> movies);
        void setLoadingIndicator(boolean isLoading);
        void showErrorMessage();
        void showEmptyMessage();
    }

    interface Presenter extends BasePresenter<View> {
        void attachView(@NonNull View view);
        void requestDataRefresh();
        void sort(@NonNull ISortConfiguration.SortType sortType);
        void start();
        void stop();

    }
}
