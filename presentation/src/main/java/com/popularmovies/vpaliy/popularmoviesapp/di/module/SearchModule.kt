package com.popularmovies.vpaliy.popularmoviesapp.di.module

import com.popularmovies.vpaliy.domain.entity.Actor
import com.popularmovies.vpaliy.domain.entity.Movie
import com.popularmovies.vpaliy.domain.entity.TVShow
import com.popularmovies.vpaliy.domain.interactor.SearchInteractor
import com.popularmovies.vpaliy.popularmoviesapp.ui.search.SearchContract
import com.popularmovies.vpaliy.popularmoviesapp.ui.search.SearchPresenter
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope
import dagger.Module
import dagger.Provides

@Module
class SearchModule{
  @ViewScope
  @Provides
  internal fun searchMovies(interactor:SearchInteractor<Movie>)
          :SearchContract.Presenter<Movie>
          =SearchPresenter(interactor)

  @ViewScope
  @Provides
  internal fun searchTV(interactor:SearchInteractor<TVShow>)
          :SearchContract.Presenter<TVShow>
          =SearchPresenter(interactor)

  @ViewScope
  @Provides
  internal fun searchPeople(interactor:SearchInteractor<Actor>)
          :SearchContract.Presenter<Actor>
          =SearchPresenter(interactor)
}