package com.popularmovies.vpaliy.popularmoviesapp.di.module;

import com.popularmovies.vpaliy.data.utils.scheduler.BaseSchedulerProvider;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import com.popularmovies.vpaliy.domain.repository.ICoverRepository;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MovieDetailsContract;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MediaContract;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MovieCastContract;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MovieInfoContract;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MovieReviewContract;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.presenter.MovieDetailsPresenter;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.presenter.MediaPresenter;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.presenter.MovieCastPresenter;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.presenter.MovieInfoPresenter;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.presenter.MovieReviewPresenter;

import com.popularmovies.vpaliy.data.source.qualifier.Movies;
import com.popularmovies.vpaliy.data.source.qualifier.TV;
import dagger.Module;
import dagger.Provides;
import android.support.annotation.NonNull;
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope;

@Module
public class PresenterModule {

    @ViewScope
    @Provides
    @TV
    MediaContract.Presenter provideTvShowPresenter(@TV ICoverRepository<MediaCover> iCoverRepository,
                                                  BaseSchedulerProvider schedulerProvider){
        return new MediaPresenter(iCoverRepository,schedulerProvider);
    }

    @ViewScope
    @Provides
    @Movies
    MediaContract.Presenter provideMoviesPresenter(@Movies ICoverRepository<MediaCover> iCoverRepository,
                                                   BaseSchedulerProvider schedulerProvider){
        return new MediaPresenter(iCoverRepository,schedulerProvider);
    }

    @ViewScope
    @Provides
    MovieDetailsContract.Presenter provideMovieDetailsPresenter(@NonNull MovieDetailsPresenter presenter){
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

    @ViewScope
    @Provides
    MovieReviewContract.Presenter provideMovieReviewPresenter(@NonNull MovieReviewPresenter presenter){
        return presenter;
    }



}
