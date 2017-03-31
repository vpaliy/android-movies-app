package com.popularmovies.vpaliy.popularmoviesapp.presentation.di.module;

import android.support.annotation.NonNull;

import com.popularmovies.vpaliy.popularmoviesapp.presentation.di.scope.ViewScope;
import com.popularmovies.vpaliy.popularmoviesapp.presentation.mvp.contract.DetailsMovieContract;
import com.popularmovies.vpaliy.popularmoviesapp.presentation.mvp.contract.MoviesContract;
import com.popularmovies.vpaliy.popularmoviesapp.presentation.mvp.presenter.DetailsMoviePresenter;
import com.popularmovies.vpaliy.popularmoviesapp.presentation.mvp.presenter.MoviesPresenter;

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
