package com.popularmovies.vpaliy.popularmoviesapp.ui.detail

import com.popularmovies.vpaliy.domain.entity.Movie
import com.popularmovies.vpaliy.domain.entity.SimilarityType
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope
import com.popularmovies.vpaliy.popularmoviesapp.ui.detail.DetailContract.View
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaFacade

@ViewScope
class DetailPresenter(private val facade: MediaFacade<Movie>):DetailContract.Presenter{
    lateinit var view:View

    override fun attachView(view: View) {
        this.view=view
    }

    override fun start() {
        facade.fetchItem(view::showMedia,this::handleError)
        facade.fetchReviews(view::showReviews,this::handleError)
        facade.fetchRoles(view::showCast,this::handleError)
        facade.fetchTrailers(view::showTrailers,this::handleError)
    }

    override fun stop() {}

    override fun more(type: SimilarityType) {
        TODO("Implement")
    }

    private fun handleError(ex:Throwable){
        ex.printStackTrace()
    }

    override fun attachId(id: String) {
        facade.id=id
    }
}