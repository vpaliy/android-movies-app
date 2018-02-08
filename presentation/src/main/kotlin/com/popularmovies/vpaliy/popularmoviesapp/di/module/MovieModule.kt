package com.popularmovies.vpaliy.popularmoviesapp.di.module

import com.popularmovies.vpaliy.data.mapper.Mapper
import com.popularmovies.vpaliy.domain.entity.Movie
import com.popularmovies.vpaliy.domain.interactor.*
import com.popularmovies.vpaliy.popularmoviesapp.ui.home.HomeContract
import com.popularmovies.vpaliy.popularmoviesapp.ui.home.HomePresenter
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaModel
import com.popularmovies.vpaliy.popularmoviesapp.ui.more.MoreContract
import com.popularmovies.vpaliy.popularmoviesapp.ui.more.MorePresenter
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
  fun home(interactor: GetPage<Movie>, mapper: Mapper<MediaModel, Movie>)
      : HomeContract.Presenter
      = HomePresenter(interactor, mapper)

  @ViewScope
  @Provides
  fun more(interactor: GetPage<Movie>, mapper: Mapper<MediaModel, Movie>)
      : MoreContract.Presenter
      = MorePresenter(interactor, mapper)

  @ViewScope
  @Provides
  fun details(item: GetMediaItem<Movie>, reviews: GetReviews<Movie>, trailers: GetTrailers<Movie>, roles: GetRoles<Movie>,
              suggestion: GetSuggestion<Movie>, mapper: Mapper<MediaModel, Movie>): DetailContract.Presenter {
    val facade = MediaFacade(item, reviews, trailers, roles, suggestion)
    return DetailPresenter(facade, mapper)
  }
}