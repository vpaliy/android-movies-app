package com.popularmovies.vpaliy.popularmoviesapp.ui.home

import com.popularmovies.vpaliy.domain.entity.*
import com.popularmovies.vpaliy.popularmoviesapp.App
import com.popularmovies.vpaliy.popularmoviesapp.R
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerMovieComponent
import com.popularmovies.vpaliy.popularmoviesapp.di.module.MovieModule
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.TYPES
import com.vpaliy.kotlin_extensions.getColor
import javax.inject.Inject

class MoviesFragment : HomeFragment() {
  override var presenter: HomeContract.Presenter? = null
    @Inject set(value) {
      field = value
      field?.attach(this)
    }

  override fun types() = arrayOf(Popular, Top, Upcoming, NowPlaying)

  override fun getColor(type: MediaType) = getColor(R.color.colorMovies)

  override fun getTitle(type: MediaType): String = getString(TYPES[type]!!)

  override fun inject() {
    DaggerMovieComponent.builder()
        .applicationComponent(App.component)
        .movieModule(MovieModule())
        .build().inject(this)
  }
}