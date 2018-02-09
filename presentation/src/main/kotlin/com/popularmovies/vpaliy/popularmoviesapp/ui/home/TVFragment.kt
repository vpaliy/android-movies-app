package com.popularmovies.vpaliy.popularmoviesapp.ui.home

import com.popularmovies.vpaliy.domain.entity.MediaType
import com.popularmovies.vpaliy.domain.entity.Top
import com.popularmovies.vpaliy.popularmoviesapp.App
import com.popularmovies.vpaliy.popularmoviesapp.R
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerTVComponent
import com.popularmovies.vpaliy.popularmoviesapp.di.module.TVModule
import com.vpaliy.kotlin_extensions.getColor
import javax.inject.Inject

class TVFragment : HomeFragment() {
  override var presenter: HomeContract.Presenter? = null
    @Inject set(value) {
      field = value
      field?.attach(this)
    }

  private val typeMap by lazy(LazyThreadSafetyMode.NONE) {
    HashMap<MediaType, String>().apply {
      put(Top, getString(R.string.top_rated_media))
    }
  }

  override fun types() = typeMap.keys.toTypedArray()

  override fun getColor(type: MediaType) = getColor(R.color.colorTvShows)

  override fun getTitle(type: MediaType) = typeMap[type]!!

  override fun inject() {
    DaggerTVComponent.builder()
        .applicationComponent(App.component)
        .tVModule(TVModule())
        .build().inject(this)
  }
}