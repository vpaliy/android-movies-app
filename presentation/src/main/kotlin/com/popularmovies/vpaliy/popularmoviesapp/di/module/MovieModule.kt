package com.popularmovies.vpaliy.popularmoviesapp.di.module

import com.popularmovies.vpaliy.data.mapper.Mapper
import com.popularmovies.vpaliy.domain.entity.Movie
import com.popularmovies.vpaliy.domain.interactor.*
import com.popularmovies.vpaliy.popularmoviesapp.ui.home.HomeContract
import com.popularmovies.vpaliy.popularmoviesapp.ui.home.HomePresenter
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaModel
import com.popularmovies.vpaliy.popularmoviesapp.ui.detail.DetailContract
import com.popularmovies.vpaliy.popularmoviesapp.ui.detail.DetailPresenter
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaFacade
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope
import dagger.Module
import dagger.Provides

@Module
class MovieModule {
  @ViewScope
  @Provides
  fun presenter(interactor: GetPage<Movie>, mapper: Mapper<MediaModel, Movie>)
      : HomeContract.Presenter
      = HomePresenter(interactor, mapper)
}