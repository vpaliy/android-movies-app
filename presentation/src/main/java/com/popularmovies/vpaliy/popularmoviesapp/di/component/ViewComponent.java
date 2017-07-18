package com.popularmovies.vpaliy.popularmoviesapp.di.component;

import com.popularmovies.vpaliy.popularmoviesapp.di.module.PresenterModule;
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope;
import com.popularmovies.vpaliy.popularmoviesapp.ui.actor.ActorFragment;
import com.popularmovies.vpaliy.popularmoviesapp.ui.details.MediaDetailsFragment;
import com.popularmovies.vpaliy.popularmoviesapp.ui.details.MovieDetailsFragment;
import com.popularmovies.vpaliy.popularmoviesapp.ui.details.TvDetailsFragment;
import com.popularmovies.vpaliy.popularmoviesapp.ui.media.MediaActivity;
import com.popularmovies.vpaliy.popularmoviesapp.ui.media.MediaFragment;
import com.popularmovies.vpaliy.popularmoviesapp.ui.more.MoreMoviesFragment;
import com.popularmovies.vpaliy.popularmoviesapp.ui.more.MoreTvFragment;
import com.popularmovies.vpaliy.popularmoviesapp.ui.media.MoviesFragment;
import com.popularmovies.vpaliy.popularmoviesapp.ui.media.PersonalFragment;
import com.popularmovies.vpaliy.popularmoviesapp.ui.media.TvShowsFragment;
import com.popularmovies.vpaliy.popularmoviesapp.ui.season.SeasonFragment;

import dagger.Component;

@ViewScope
@Component(dependencies = ApplicationComponent.class, modules = PresenterModule.class)
public interface ViewComponent {
    void inject(MediaFragment mediaFragment);
    void inject(TvShowsFragment tvShowsFragment);
    void inject(MediaActivity activity);
    void inject(PersonalFragment personalFragment);
    void inject(MoreMoviesFragment fragment);
    void inject(MoreTvFragment fragment);
    void inject(MoviesFragment fragment);
    void inject(MediaDetailsFragment fragment);
    void inject(ActorFragment fragment);
    void inject(TvDetailsFragment fragment);
    void inject(MovieDetailsFragment fragment);
    void inject(SeasonFragment fragment);
}
