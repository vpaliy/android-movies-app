package com.popularmovies.vpaliy.popularmoviesapp.di.module;

import android.support.annotation.NonNull;

import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.DetailsMovieContract;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MoviesContract;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.presenter.DetailsMoviePresenter;
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
    DetailsMovieContract.Presenter provideDetailsPresenter(@NonNull DetailsMoviePresenter presenter){
        return presenter;
    }


}
