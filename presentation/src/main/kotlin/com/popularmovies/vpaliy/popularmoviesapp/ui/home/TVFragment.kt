package com.popularmovies.vpaliy.popularmoviesapp.ui.home

import com.popularmovies.vpaliy.domain.entity.*
import com.popularmovies.vpaliy.popularmoviesapp.App
import com.popularmovies.vpaliy.popularmoviesapp.R
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerTVComponent
import com.popularmovies.vpaliy.popularmoviesapp.di.module.TVModule
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.TYPES
import com.vpaliy.kotlin_extensions.getColor
import javax.inject.Inject

class TVFragment : HomeFragment() {
  override var presenter: HomeContract.Presenter? = null
    @Inject set(value) {
      field = value
      field?.attach(this)
    }

  override fun types() = arrayOf(Popular, Airing, Top, Upcoming)

  override fun getColor(type: MediaType) = getColor(R.color.colorTvShows)

  override fun getTitle(type: MediaType): String = getString(TYPES[type]!!)

  override fun inject() {
    DaggerTVComponent.builder()
        .applicationComponent(App.component)
        .tVModule(TVModule())
        .build().inject(this)
  }
}