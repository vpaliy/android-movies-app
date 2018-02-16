package com.popularmovies.vpaliy.popularmoviesapp.ui.detail

import android.support.annotation.StringRes
import com.popularmovies.vpaliy.domain.entity.*
import com.popularmovies.vpaliy.domain.interactor.params.Suggestion
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseView
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaModel

object DetailContract {
  interface View : BaseView {
    fun showReviews(data: List<Review>)
    fun showTrailers(data: List<Trailer>)
    fun showBackdrops(data: List<String>)
    fun showCast(data: List<Role>)
    fun showMedia(movie: Movie)
    fun showMessage(@StringRes resource: Int)
    fun showSuggested(type: SimilarityType, data: List<MediaModel>)
    fun appendSuggested(type: SimilarityType, data: List<MediaModel>)
  }

  interface Presenter {
    fun attachView(view: View)
    fun more(type: SimilarityType)
    fun start()
  }
}