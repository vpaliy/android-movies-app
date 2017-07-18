package com.popularmovies.vpaliy.popularmoviesapp.di.module;

import com.popularmovies.vpaliy.data.utils.scheduler.BaseSchedulerProvider;
import com.popularmovies.vpaliy.domain.model.ActorDetails;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import com.popularmovies.vpaliy.domain.model.MovieDetails;
import com.popularmovies.vpaliy.domain.model.TVShowDetails;
import com.popularmovies.vpaliy.domain.repository.ICoverRepository;
import com.popularmovies.vpaliy.domain.repository.IDetailsRepository;
import com.popularmovies.vpaliy.popularmoviesapp.ui.actor.ActorContract;
import com.popularmovies.vpaliy.popularmoviesapp.ui.actor.ActorPresenter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.details.DetailsPresenter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.details.MediaDetailsContract;
import com.popularmovies.vpaliy.popularmoviesapp.ui.details.MovieDetailsPresenter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.details.TvDetailsPresenter;
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

    @ViewScope @Provides @Movies
    MediaDetailsContract.Presenter movieDetailsPresenter(@Movies ICoverRepository<MediaCover> coverRepository,
                                                         IDetailsRepository<MovieDetails> detailsRepository,
                                                         BaseSchedulerProvider schedulerProvider){
        return new MovieDetailsPresenter(detailsRepository,coverRepository,schedulerProvider);
    }

    @ViewScope @Provides @TV
    MediaDetailsContract.Presenter tvDetailsPresenter(@TV ICoverRepository<MediaCover> coverRepository,
                                                      IDetailsRepository<TVShowDetails> detailsRepository,
                                                      BaseSchedulerProvider schedulerProvider){
        return new TvDetailsPresenter(detailsRepository,coverRepository,schedulerProvider);
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

    @ViewScope @Provides
    ActorContract.Presenter actorPresenter(IDetailsRepository<ActorDetails> iDetailsRepository,
                                           BaseSchedulerProvider schedulerProvider){
        return new ActorPresenter(iDetailsRepository,schedulerProvider);
    }

}
