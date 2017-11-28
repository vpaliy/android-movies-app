package com.popularmovies.vpaliy.popularmoviesapp.ui.search

import com.popularmovies.vpaliy.data.mapper.Mapper
import com.popularmovies.vpaliy.domain.entity.TVShow
import com.popularmovies.vpaliy.popularmoviesapp.App
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerSearchComponent
import com.popularmovies.vpaliy.popularmoviesapp.di.module.SearchModule
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaModel
import kotlinx.android.synthetic.main.fragment_search.*
import javax.inject.Inject

class TVResult:SearchResult<TVShow>(){
  override var presenter: SearchContract.Presenter<TVShow>?=null
    @Inject set(value) {
      field=value
      field?.attachView(this)
    }


  @Inject lateinit var mapper: Mapper<MediaModel, TVShow>

  private val adapter by lazy { MediaAdapter(context) }

  override fun appendResult(data: List<TVShow>) {
    adapter.append(mapper.map(data).toMutableList())
  }

  override fun empty() {}

  override fun error() {}

  override fun onCleared() {}

  override fun showResult(data: List<TVShow>) {
    result.adapter=adapter
    adapter.data=mapper.map(data).toMutableList()
  }

  override fun inject() {
    DaggerSearchComponent.builder()
            .applicationComponent(App.component)
            .searchModule(SearchModule())
            .build().inject(this)
  }
}