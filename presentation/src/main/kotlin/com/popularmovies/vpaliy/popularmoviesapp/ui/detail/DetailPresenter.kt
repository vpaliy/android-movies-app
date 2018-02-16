package com.popularmovies.vpaliy.popularmoviesapp.ui.detail

import com.popularmovies.vpaliy.data.mapper.Mapper
import com.popularmovies.vpaliy.domain.entity.Movie
import com.popularmovies.vpaliy.domain.entity.SimilarityType
import com.popularmovies.vpaliy.domain.interactor.params.Suggestion
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope
import com.popularmovies.vpaliy.popularmoviesapp.ui.detail.DetailContract.View
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaFacade
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaModel
import com.vpaliy.kotlin_extensions.info

@ViewScope
class DetailPresenter(private val facade: MediaFacade<Movie>, private val mapper: Mapper<MediaModel, Movie>)
  : DetailContract.Presenter {
  lateinit var view: View

  override fun attachView(view: View) {
    this.view = view
  }

  override fun start() {
    info("in start method")
    facade.getItem(view::showMedia, this::handleError)
    facade.getRoles(view::showCast, this::handleError)
    facade.getTrailers(view::showTrailers, this::handleError)
    SimilarityType.all().forEach {
      facade.getSuggestion(this::catchSuggestion, this::handleError, it)
    }
  }

  override fun more(type: SimilarityType) {
    facade.moreSuggestions(this::appendSuggestion, this::handleError, type)
  }

  private fun appendSuggestion(suggestion: Suggestion, data: List<Movie>) {
    view.appendSuggested(suggestion.type, mapper.map(data))
  }

  private fun catchSuggestion(suggestion: Suggestion, data: List<Movie>) {
    view.showSuggested(suggestion.type, mapper.map(data))
  }

  private fun handleError(ex: Throwable) {
    ex.printStackTrace()
  }
}