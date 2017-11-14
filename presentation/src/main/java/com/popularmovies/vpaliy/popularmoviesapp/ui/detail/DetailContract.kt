package com.popularmovies.vpaliy.popularmoviesapp.ui.detail

import android.support.annotation.StringRes
import com.popularmovies.vpaliy.domain.entity.*
import com.popularmovies.vpaliy.domain.interactor.params.Suggestion
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaModel

object DetailContract{
    interface View{
        fun showReviews(data:List<Review>)
        fun showTrailers(data:List<Trailer>)
        fun showBackdrops(data:List<String>)
        fun showTags(data:List<String>)
        fun showCast(data:List<Role>)
        fun showMedia(media:MediaModel)
        fun showMessage(@StringRes resource:Int)
        fun showSuggested(type:Suggestion<Movie>, data:List<MediaModel>)
    }

    interface Presenter{
        fun attachView(view:View)
        fun more(type: SimilarityType)
        fun start()
        fun stop()
    }
}