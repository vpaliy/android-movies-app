package com.popularmovies.vpaliy.popularmoviesapp.di.component;

import android.content.Context;

import com.popularmovies.vpaliy.domain.IRepository;
import com.popularmovies.vpaliy.domain.ISortConfiguration;
import com.popularmovies.vpaliy.domain.model.MovieCover;
import com.popularmovies.vpaliy.domain.model.MovieDetails;
import com.popularmovies.vpaliy.popularmoviesapp.di.module.ApplicationModule;
import com.popularmovies.vpaliy.popularmoviesapp.di.module.DataModule;
import com.popularmovies.vpaliy.popularmoviesapp.ui.activity.BaseActivity;
import com.popularmovies.vpaliy.popularmoviesapp.ui.navigator.Navigator;
import com.squareup.otto.Bus;

import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, DataModule.class})
public interface ApplicationComponent {

    void inject(BaseActivity activity);

    Context context();
    Bus eventBus();
    Navigator navigator();
    IRepository<MovieCover,MovieDetails> moviesRepository();
    ISortConfiguration sortConfiguration();
}
