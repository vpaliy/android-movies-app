package com.popularmovies.vpaliy.popularmoviesapp.di.component;

import com.popularmovies.vpaliy.popularmoviesapp.di.module.PresenterModule;
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope;
import com.popularmovies.vpaliy.popularmoviesapp.ui.activity.MoviesActivity;
import com.popularmovies.vpaliy.popularmoviesapp.ui.fragment.MovieCastFragment;
import com.popularmovies.vpaliy.popularmoviesapp.ui.fragment.MovieDetailsFragment;
import com.popularmovies.vpaliy.popularmoviesapp.ui.fragment.MovieInfoFragment;
import com.popularmovies.vpaliy.popularmoviesapp.ui.fragment.MovieReviewFragment;
import com.popularmovies.vpaliy.popularmoviesapp.ui.fragment.MoviesFragment;

import dagger.Component;

@ViewScope
@Component(dependencies = ApplicationComponent.class, modules = PresenterModule.class)
public interface ViewComponent {
    void inject(MoviesFragment fragment);
    void inject(MovieDetailsFragment fragment);
    void inject(MovieCastFragment fragment);
    void inject(MovieInfoFragment fragment);
    void inject(MovieReviewFragment fragment);
}
