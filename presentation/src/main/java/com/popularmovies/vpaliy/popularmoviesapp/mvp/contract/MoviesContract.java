package com.popularmovies.vpaliy.popularmoviesapp.mvp.contract;

import android.support.annotation.NonNull;

import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration.SortType;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.BasePresenter;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.BaseView;

import java.util.List;

public interface MoviesContract {

    interface View extends BaseView<Presenter> {
        void attachPresenter(@NonNull Presenter presenter);
        void showMovies(@NonNull SortType sortType, @NonNull List<MediaCover> movies);
        void appendMovies(@NonNull SortType sortType, @NonNull List<MediaCover> movies);
        void setLoadingIndicator(boolean isLoading);
        void showErrorMessage();
        void showEmptyMessage();
    }

    interface Presenter extends BasePresenter<View> {
        void attachView(@NonNull View view);
        void requestDataRefresh(@NonNull SortType sortType);
        void requestMoreData(@NonNull SortType sortType);
        void start(SortType sortType);
        void stop();

    }
}
