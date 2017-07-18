package com.popularmovies.vpaliy.popularmoviesapp.di.component;

import android.content.Context;
import com.popularmovies.vpaliy.data.utils.scheduler.BaseSchedulerProvider;
import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration;
import com.popularmovies.vpaliy.domain.model.ActorDetails;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import com.popularmovies.vpaliy.domain.model.MovieDetails;
import com.popularmovies.vpaliy.domain.model.TVShowDetails;
import com.popularmovies.vpaliy.domain.repository.ICoverRepository;
import com.popularmovies.vpaliy.domain.repository.IDetailsRepository;
import com.popularmovies.vpaliy.popularmoviesapp.di.module.ApplicationModule;
import com.popularmovies.vpaliy.popularmoviesapp.di.module.DataModule;
import com.popularmovies.vpaliy.popularmoviesapp.di.module.MapperModule;
import com.popularmovies.vpaliy.popularmoviesapp.di.module.NetworkModule;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseActivity;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.bus.RxBus;
import com.popularmovies.vpaliy.popularmoviesapp.ui.navigator.Navigator;

import com.popularmovies.vpaliy.data.source.qualifier.Movies;
import com.popularmovies.vpaliy.data.source.qualifier.TV;
import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, DataModule.class,
        NetworkModule.class, MapperModule.class})
public interface ApplicationComponent {
    void inject(BaseActivity activity);
    Context context();
    RxBus rxEventBus();
    Navigator navigator();
    ISortConfiguration sortConfiguration();
    BaseSchedulerProvider schedulerProvider();
    IDetailsRepository<ActorDetails> provideActorDetails();
    IDetailsRepository<MovieDetails> provideMovieDetails();
    IDetailsRepository<TVShowDetails> provideTvDetails();
    @Movies ICoverRepository<MediaCover> moviesRepository();
    @TV ICoverRepository<MediaCover> tvRepository();
}
