package com.popularmovies.vpaliy.popularmoviesapp.di.component;

import android.content.Context;

import com.popularmovies.vpaliy.data.source.remote.MovieDatabaseAPI;
import com.popularmovies.vpaliy.data.utils.SchedulerProvider;
import com.popularmovies.vpaliy.domain.IMovieRepository;
import com.popularmovies.vpaliy.domain.IRepository;
import com.popularmovies.vpaliy.domain.ISortConfiguration;
import com.popularmovies.vpaliy.domain.model.MovieCover;
import com.popularmovies.vpaliy.domain.model.MovieDetails;
import com.popularmovies.vpaliy.popularmoviesapp.di.module.ApplicationModule;
import com.popularmovies.vpaliy.popularmoviesapp.di.module.DataModule;
import com.popularmovies.vpaliy.popularmoviesapp.di.module.NetworkModule;
import com.popularmovies.vpaliy.popularmoviesapp.ui.activity.BaseActivity;
import com.popularmovies.vpaliy.popularmoviesapp.ui.eventBus.RxBus;
import com.popularmovies.vpaliy.popularmoviesapp.ui.navigator.Navigator;
import com.squareup.otto.Bus;

import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, DataModule.class, NetworkModule.class})
public interface ApplicationComponent {

    void inject(BaseActivity activity);

    Context context();
    RxBus rxEventBus();
    Navigator navigator();
    IRepository<MovieCover,MovieDetails> repository();
    IMovieRepository<MovieCover,MovieDetails> movieRepository();
    ISortConfiguration sortConfiguration();
    MovieDatabaseAPI provideMovieDataBaseAPI();
    SchedulerProvider schedulerProvider();
}
