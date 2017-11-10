package com.popularmovies.vpaliy.popularmoviesapp.ui.search

import com.popularmovies.vpaliy.domain.entity.Actor
import com.popularmovies.vpaliy.popularmoviesapp.App
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerSearchComponent
import com.popularmovies.vpaliy.popularmoviesapp.di.module.SearchModule
import kotlinx.android.synthetic.main.fragment_search.*
import javax.inject.Inject

class PeopleResult:SearchResult<Actor>(){

    override var presenter:SearchContract.Presenter<Actor>?=null
        @Inject set(value) {
            field=value
            field?.attachView(this)
        }

    private val adapter by lazy { ActorResultAdapter(context) }

    override fun appendResult(data: List<Actor>) {}

    override fun empty() {}

    override fun error() {}

    override fun onCleared() {}

    override fun showResult(data: List<Actor>) {
        adapter.data=data.toMutableList()
        result.adapter=adapter
    }

    override fun inject() {
        DaggerSearchComponent.builder()
                .applicationComponent(App.component)
                .searchModule(SearchModule())
                .build().inject(this)
    }
}