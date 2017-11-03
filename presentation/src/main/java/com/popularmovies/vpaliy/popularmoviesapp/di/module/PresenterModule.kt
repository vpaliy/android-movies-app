package com.popularmovies.vpaliy.popularmoviesapp.di.module

import com.popularmovies.vpaliy.data.mapper.Mapper
import com.popularmovies.vpaliy.domain.entity.Movie
import com.popularmovies.vpaliy.domain.entity.TVShow
import com.popularmovies.vpaliy.domain.interactor.GetPage
import com.popularmovies.vpaliy.popularmoviesapp.di.qualifier.Movies
import com.popularmovies.vpaliy.popularmoviesapp.di.qualifier.TV
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope
import com.popularmovies.vpaliy.popularmoviesapp.ui.home.HomeContract
import com.popularmovies.vpaliy.popularmoviesapp.ui.home.MoviesPresenter
import com.popularmovies.vpaliy.popularmoviesapp.ui.home.TVPresenter
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaModel
import dagger.Module
import dagger.Provides

@Module
class PresenterModule{
    @Movies
    @ViewScope
    @Provides
    internal fun movies(interactor:GetPage<Movie>, mapper: Mapper<MediaModel, Movie>)
            :HomeContract.Presenter=MoviesPresenter(interactor,mapper)

    @TV
    @ViewScope
    @Provides
    internal fun tv(interactor:GetPage<TVShow>, mapper:Mapper<MediaModel, TVShow>)
            :HomeContract.Presenter=TVPresenter(interactor,mapper)
}
