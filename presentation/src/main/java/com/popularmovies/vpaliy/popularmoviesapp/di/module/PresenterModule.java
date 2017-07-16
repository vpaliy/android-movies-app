package com.popularmovies.vpaliy.popularmoviesapp.di.module;

import com.popularmovies.vpaliy.data.utils.scheduler.BaseSchedulerProvider;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import com.popularmovies.vpaliy.domain.repository.ICoverRepository;
import com.popularmovies.vpaliy.popularmoviesapp.ui.details.DetailsPresenter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.details.MediaDetailsContract;
import com.popularmovies.vpaliy.popularmoviesapp.ui.media.MediaContract;
import com.popularmovies.vpaliy.popularmoviesapp.ui.media.MediaPresenter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.more.MoreMediaContract;
import com.popularmovies.vpaliy.popularmoviesapp.ui.more.MoreMediaPresenter;

import dagger.Module;
import dagger.Provides;
import android.support.annotation.NonNull;
import com.popularmovies.vpaliy.data.source.qualifier.Movies;
import com.popularmovies.vpaliy.data.source.qualifier.TV;
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope;

@Module
public class PresenterModule {

    @ViewScope @Provides @TV
    MediaContract.Presenter tvShowPresenter(@TV ICoverRepository<MediaCover> iCoverRepository,
                                                  BaseSchedulerProvider schedulerProvider){
        return new MediaPresenter(iCoverRepository,schedulerProvider);
    }

    @ViewScope @Provides @Movies
    MediaContract.Presenter moviesPresenter(@Movies ICoverRepository<MediaCover> iCoverRepository,
                                                   BaseSchedulerProvider schedulerProvider){
        return new MediaPresenter(iCoverRepository,schedulerProvider);
    }

    @ViewScope @Provides
    MediaDetailsContract.Presenter detailsPresenter(@NonNull DetailsPresenter presenter){
        return presenter;
    }

    @ViewScope @Provides @TV
    MoreMediaContract.Presenter moreTvShowPresenter(@TV ICoverRepository<MediaCover> iCoverRepository,
                                                          BaseSchedulerProvider schedulerProvider){
        return new MoreMediaPresenter(iCoverRepository,schedulerProvider);
    }

    @ViewScope @Provides @Movies
    MoreMediaContract.Presenter moreMoviesPresenter(@Movies ICoverRepository<MediaCover> iCoverRepository,
                                                           BaseSchedulerProvider schedulerProvider){
        return new MoreMediaPresenter(iCoverRepository,schedulerProvider);
    }

}
