package com.popularmovies.vpaliy.popularmoviesapp.ui.media;

import com.popularmovies.vpaliy.domain.configuration.SortType;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BasePresenter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseView;
import java.util.List;

import android.support.annotation.NonNull;

public interface MediaContract {

    interface View extends BaseView<Presenter> {
        void attachPresenter(@NonNull Presenter presenter);
        void showMedia(@NonNull SortType sortType, @NonNull List<MediaCover> movies);
        void appendMedia(@NonNull SortType sortType, @NonNull List<MediaCover> movies);
        void setLoadingIndicator(boolean isLoading);
        void showErrorMessage();
        void showEmptyMessage();
    }

    interface Presenter extends BasePresenter<View> {
        void attachView(@NonNull View view);
        void requestRefresh(@NonNull SortType sortType);
        void requestMore(@NonNull SortType sortType);
        void start(SortType sortType);
        void stop();
    }
}
