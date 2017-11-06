package com.popularmovies.vpaliy.popularmoviesapp.ui.search

import com.popularmovies.vpaliy.domain.entity.Actor
import javax.inject.Inject

class PeopleResult:SearchResult<Actor>(){

    override var presenter:SearchContract.Presenter<Actor>?=null
        @Inject set(value) {
            field=value
            field?.attachView(this)
        }

    override fun appendResult(data: List<Actor>) {}

    override fun empty() {}

    override fun error() {}

    override fun showResult(data: List<Actor>) {
    }
}