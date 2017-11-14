package com.popularmovies.vpaliy.popularmoviesapp.ui.detail

import com.popularmovies.vpaliy.domain.entity.SimilarityType
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope
import com.popularmovies.vpaliy.popularmoviesapp.ui.detail.DetailContract.View
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaFacade

@ViewScope
class DetailPresenter(facade: MediaFacade<*>):DetailContract.Presenter{
    lateinit var view:View
    lateinit var id:String

    override fun attachView(view: View) {
        this.view=view
    }

    override fun start() {

    }

    override fun stop() {
    }

    override fun more(type: SimilarityType) {

    }
}