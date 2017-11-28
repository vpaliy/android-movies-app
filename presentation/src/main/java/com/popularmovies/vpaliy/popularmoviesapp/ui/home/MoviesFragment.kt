package com.popularmovies.vpaliy.popularmoviesapp.ui.home

import com.popularmovies.vpaliy.domain.entity.MediaType
import com.popularmovies.vpaliy.popularmoviesapp.App
import com.popularmovies.vpaliy.popularmoviesapp.R
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerMovieComponent
import com.popularmovies.vpaliy.popularmoviesapp.di.module.MovieModule
import com.vpaliy.kotlin_extensions.getColor
import javax.inject.Inject

class MoviesFragment:HomeFragment(){
  override var presenter: HomeContract.Presenter?=null
    @Inject set(value) {
      field=value
      field?.attach(this)
    }
  override fun types()= arrayOf(MediaType.POPULAR,MediaType.TOP,
          MediaType.UPCOMING,MediaType.NOW_PLAYING)

  override fun getColor(type: MediaType)=getColor(R.color.colorMovies)

  override fun getTitle(type: MediaType): String {
    return when(type){
      MediaType.POPULAR -> getString(R.string.popular_media)
      MediaType.NOW_PLAYING -> getString(R.string.now_playing_media)
      MediaType.UPCOMING -> getString(R.string.upcoming_media)
      MediaType.TOP -> getString(R.string.top_rated_media)
      else -> throw IllegalArgumentException()
    }
  }

  override fun inject() {
    DaggerMovieComponent.builder()
            .applicationComponent(App.component)
            .movieModule(MovieModule())
            .build().inject(this)
  }
}