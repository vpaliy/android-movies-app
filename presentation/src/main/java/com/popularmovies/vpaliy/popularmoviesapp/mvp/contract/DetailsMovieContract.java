package com.popularmovies.vpaliy.popularmoviesapp.mvp.contract;

import android.support.annotation.NonNull;

import com.popularmovies.vpaliy.domain.model.MovieCover;
import com.popularmovies.vpaliy.domain.model.MovieDetails;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.BasePresenter;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.BaseView;

public interface DetailsMovieContract {

    interface View extends BaseView<Presenter> {
        void attachPresenter(@NonNull Presenter presenter);
        void showCover(@NonNull MovieCover movieCover);
        void showDetails(@NonNull MovieDetails movieDetails);

    }

    interface Presenter extends BasePresenter<View> {
        void attachView(@NonNull View view);
        void start(int ID);
        void stop();
    }
}
