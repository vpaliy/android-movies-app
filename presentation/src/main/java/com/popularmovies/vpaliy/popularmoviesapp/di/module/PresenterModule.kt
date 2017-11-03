package com.popularmovies.vpaliy.popularmoviesapp.di.module

import com.popularmovies.vpaliy.domain.entity.Movie
import com.popularmovies.vpaliy.domain.entity.TVShow
import com.popularmovies.vpaliy.domain.interactor.GetPage
import com.popularmovies.vpaliy.popularmoviesapp.di.qualifier.Movies
import com.popularmovies.vpaliy.popularmoviesapp.di.qualifier.TV
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope
import com.popularmovies.vpaliy.popularmoviesapp.ui.home.HomeContract
import com.popularmovies.vpaliy.popularmoviesapp.ui.home.MoviesPresenter
import com.popularmovies.vpaliy.popularmoviesapp.ui.home.TVPresenter
import com.popularmovies.vpaliy.popularmoviesapp.ui.mapper.MediaMovieMapper
import com.popularmovies.vpaliy.popularmoviesapp.ui.mapper.MediaTVMapper
import dagger.Module
import dagger.Provides

@Module
class PresenterModule{
    @ViewScope
    @Provides
    @Movies
    internal fun movies(interactor:GetPage<Movie>, mapper:MediaMovieMapper)
            :HomeContract.Presenter=MoviesPresenter(interactor,mapper)

    @ViewScope
    @Provides
    @TV
    internal fun tv(interactor:GetPage<TVShow>, mapper:MediaTVMapper)
            :HomeContract.Presenter=TVPresenter(interactor,mapper)
}
