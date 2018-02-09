package com.popularmovies.vpaliy.popularmoviesapp.ui.home

import com.popularmovies.vpaliy.domain.entity.*
import com.popularmovies.vpaliy.popularmoviesapp.App
import com.popularmovies.vpaliy.popularmoviesapp.R
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerMovieComponent
import com.popularmovies.vpaliy.popularmoviesapp.di.module.MovieModule
import com.vpaliy.kotlin_extensions.getColor
import javax.inject.Inject

class MoviesFragment : HomeFragment() {
  override var presenter: HomeContract.Presenter? = null
    @Inject set(value) {
      field = value
      field?.attach(this)
    }

  private val typeMap by lazy(LazyThreadSafetyMode.NONE) {
    HashMap<MediaType, String>().apply {
      put(Popular, getString(R.string.popular_media))
      put(Top, getString(R.string.top_rated_media))
      put(Upcoming, getString(R.string.upcoming_media))
      put(NowPlaying, getString(R.string.now_playing_media))
    }
  }

  override fun types() = typeMap.keys.toTypedArray()

  override fun getColor(type: MediaType) = getColor(R.color.colorMovies)

  override fun getTitle(type: MediaType) = typeMap[type]!!

  override fun inject() {
    DaggerMovieComponent.builder()
        .applicationComponent(App.component)
        .movieModule(MovieModule())
        .build().inject(this)
  }
}