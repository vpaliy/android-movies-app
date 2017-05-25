package com.popularmovies.vpaliy.popularmoviesapp.mvp.contract;

import android.support.annotation.NonNull;

import com.popularmovies.vpaliy.domain.configuration.SortType;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import com.popularmovies.vpaliy.domain.model.MovieDetails;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.BasePresenter;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.BaseView;

import java.util.List;

public interface MovieDetailsContract {

    interface View extends BaseView<Presenter> {
        void attachPresenter(@NonNull Presenter presenter);
        void showBackdrops(@NonNull List<String> backdrops);
        void showCover(@NonNull MediaCover movieCover);
        void showDetails(@NonNull MovieDetails movieDetails);
        void shareWithMovie(MovieDetails details);

    }

    interface Presenter extends BasePresenter<View> {
        void attachView(@NonNull View view);
        void make(SortType sortType);
        void shareWithMovie();
        void start(int ID);
        void stop();
    }
}
