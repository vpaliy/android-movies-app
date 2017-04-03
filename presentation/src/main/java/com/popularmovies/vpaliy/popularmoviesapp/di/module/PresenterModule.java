package com.popularmovies.vpaliy.popularmoviesapp.di.module;

import android.support.annotation.NonNull;

import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.DetailsMovieContract;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MovieCastContract;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MovieInfoContract;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MoviesContract;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.presenter.DetailsMoviePresenter;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.presenter.MovieCastPresenter;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.presenter.MovieInfoPresenter;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.presenter.MoviesPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @ViewScope
    @Provides
    MoviesContract.Presenter provideMoviePresenter(@NonNull MoviesPresenter presenter){
        return presenter;
    }

    @ViewScope
    @Provides
    DetailsMovieContract.Presenter provideMovieDetailsPresenter(@NonNull DetailsMoviePresenter presenter){
        return presenter;
    }

    @ViewScope
    @Provides
    MovieInfoContract.Presenter provideMovieInfoPresenter(@NonNull MovieInfoPresenter presenter){
        return presenter;
    }

    @ViewScope
    @Provides
    MovieCastContract.Presenter provideMovieCastPresenter(@NonNull MovieCastPresenter presenter){
        return presenter;
    }


}
