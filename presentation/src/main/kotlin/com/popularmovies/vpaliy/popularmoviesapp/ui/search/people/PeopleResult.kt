package com.popularmovies.vpaliy.popularmoviesapp.ui.search.people

import com.popularmovies.vpaliy.domain.entity.Actor
import com.popularmovies.vpaliy.popularmoviesapp.App
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerSearchComponent
import com.popularmovies.vpaliy.popularmoviesapp.di.module.SearchModule
import com.popularmovies.vpaliy.popularmoviesapp.ui.search.SearchContract
import com.popularmovies.vpaliy.popularmoviesapp.ui.search.SearchResult
import kotlinx.android.synthetic.main.fragment_search.*
import javax.inject.Inject

class PeopleResult : SearchResult<Actor>() {
  override var presenter: SearchContract.Presenter<Actor>? = null
    @Inject set(value) {
      field = value
      field?.attachView(this)
    }


  private val adapter by lazy { PeopleAdapter(context) }

  override fun appendResult(data: List<Actor>) = adapter.append(data)

  override fun empty() {}

  override fun inputCleared() = adapter.clear()

  override fun showResult(data: List<Actor>) {
    adapter.data = data.toMutableList()
    result.adapter = adapter
  }

  override fun inject() {
    DaggerSearchComponent.builder()
        .applicationComponent(App.component)
        .searchModule(SearchModule())
        .build().inject(this)
  }
}