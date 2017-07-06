package com.popularmovies.vpaliy.popularmoviesapp.di.component;

import com.popularmovies.vpaliy.popularmoviesapp.di.module.PresenterModule;
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope;
import com.popularmovies.vpaliy.popularmoviesapp.ui.activity.MediaActivity;
import com.popularmovies.vpaliy.popularmoviesapp.ui.fragment.MediaFragment;
import com.popularmovies.vpaliy.popularmoviesapp.ui.fragment.MoreMediaFragment;
import com.popularmovies.vpaliy.popularmoviesapp.ui.fragment.MoreMoviesFragment;
import com.popularmovies.vpaliy.popularmoviesapp.ui.fragment.MoreTvFragment;
import com.popularmovies.vpaliy.popularmoviesapp.ui.fragment.MovieCastFragment;
import com.popularmovies.vpaliy.popularmoviesapp.ui.fragment.MovieDetailsFragment;
import com.popularmovies.vpaliy.popularmoviesapp.ui.fragment.MovieInfoFragment;
import com.popularmovies.vpaliy.popularmoviesapp.ui.fragment.MovieReviewFragment;
import com.popularmovies.vpaliy.popularmoviesapp.ui.fragment.MoviesFragment;
import com.popularmovies.vpaliy.popularmoviesapp.ui.fragment.PersonalFragment;
import com.popularmovies.vpaliy.popularmoviesapp.ui.fragment.TvShowsFragment;

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
    void inject(MovieDetailsFragment fragment);
    void inject(MovieCastFragment fragment);
    void inject(MovieInfoFragment fragment);
    void inject(MovieReviewFragment fragment);
}
