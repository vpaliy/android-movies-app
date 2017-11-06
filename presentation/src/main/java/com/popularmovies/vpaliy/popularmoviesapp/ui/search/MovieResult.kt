package com.popularmovies.vpaliy.popularmoviesapp.ui.search

import com.popularmovies.vpaliy.domain.entity.Movie
import javax.inject.Inject

class MovieResult:SearchContract.View<Movie>{

    var presenter: SearchPresenter<Movie>?=null
        @Inject set(value) {
            field=value
            field?.attachView(this)
        }

    override fun showResult(data: List<Movie>) {
    }

    override fun appendResult(data: List<Movie>) {
    }

    override fun error() {}

    override fun empty() {}
}