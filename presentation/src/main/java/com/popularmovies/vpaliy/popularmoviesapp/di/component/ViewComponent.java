package com.popularmovies.vpaliy.popularmoviesapp.di.component;

import com.popularmovies.vpaliy.popularmoviesapp.di.module.PresenterModule;
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseActivity;
import com.popularmovies.vpaliy.popularmoviesapp.ui.details.Dummy;
import com.popularmovies.vpaliy.popularmoviesapp.ui.media.MediaActivity;
import com.popularmovies.vpaliy.popularmoviesapp.ui.media.MediaFragment;
import com.popularmovies.vpaliy.popularmoviesapp.ui.more.MoreMoviesFragment;
import com.popularmovies.vpaliy.popularmoviesapp.ui.more.MoreTvFragment;
import com.popularmovies.vpaliy.popularmoviesapp.ui.details.fragment.MovieCastFragment;
import com.popularmovies.vpaliy.popularmoviesapp.ui.details.fragment.MovieDetailsFragment;
import com.popularmovies.vpaliy.popularmoviesapp.ui.details.fragment.MovieInfoFragment;
import com.popularmovies.vpaliy.popularmoviesapp.ui.details.fragment.MovieReviewFragment;
import com.popularmovies.vpaliy.popularmoviesapp.ui.media.MoviesFragment;
import com.popularmovies.vpaliy.popularmoviesapp.ui.media.PersonalFragment;
import com.popularmovies.vpaliy.popularmoviesapp.ui.media.TvShowsFragment;

import dagger.Component;

@ViewScope
@Component(dependencies = ApplicationComponent.class, modules = PresenterModule.class)
public interface ViewComponent {
    void inject(Dummy dummy);
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
