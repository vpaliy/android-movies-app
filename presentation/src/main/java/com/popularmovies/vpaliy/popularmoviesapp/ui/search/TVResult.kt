package com.popularmovies.vpaliy.popularmoviesapp.ui.search

import com.popularmovies.vpaliy.domain.entity.TVShow

class TVResult:SearchResult<TVShow>(){

    override var presenter: SearchContract.Presenter<TVShow>?=null
        set(value) {}

    override fun appendResult(data: List<TVShow>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun empty() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun error() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showResult(data: List<TVShow>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}