package com.popularmovies.vpaliy.popularmoviesapp.di.module

import com.popularmovies.vpaliy.data.mapper.Mapper
import com.popularmovies.vpaliy.domain.entity.Movie
import com.popularmovies.vpaliy.domain.interactor.*
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope
import com.popularmovies.vpaliy.popularmoviesapp.ui.detail.DetailContract
import com.popularmovies.vpaliy.popularmoviesapp.ui.detail.DetailPresenter
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaFacade
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaModel
import dagger.Module
import dagger.Provides

@Module
class DetailsModule(val model: MediaModel) {
  @ViewScope
  @Provides
  fun presenter(facade: MediaFacade<Movie>, mapper: Mapper<MediaModel, Movie>)
      : DetailContract.Presenter = DetailPresenter(facade, mapper)

  @ViewScope
  @Provides
  fun facade(item: GetMediaItem<Movie>, reviews: GetReviews<Movie>, trailers: GetTrailers<Movie>, roles: GetRoles<Movie>,
             suggestion: GetSuggestion<Movie>) =
      MediaFacade(item, reviews, trailers, roles, suggestion, model)
}