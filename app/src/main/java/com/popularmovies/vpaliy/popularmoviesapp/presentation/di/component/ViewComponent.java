package com.popularmovies.vpaliy.popularmoviesapp.presentation.di.component;

import com.popularmovies.vpaliy.popularmoviesapp.presentation.di.module.PresenterModule;
import com.popularmovies.vpaliy.popularmoviesapp.presentation.ui.fragment.MovieDetailsFragment;
import com.popularmovies.vpaliy.popularmoviesapp.presentation.ui.fragment.MoviesFragment;

import com.popularmovies.vpaliy.popularmoviesapp.presentation.di.scope.ViewScope;
import dagger.Component;

@ViewScope
@Component(dependencies = ApplicationComponent.class, modules = PresenterModule.class)
public interface ViewComponent {
    void inject(MoviesFragment fragment);
    void inject(MovieDetailsFragment fragment);
}
