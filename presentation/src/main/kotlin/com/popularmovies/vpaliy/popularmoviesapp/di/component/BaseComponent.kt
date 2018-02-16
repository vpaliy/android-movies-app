package com.popularmovies.vpaliy.popularmoviesapp.di.component

interface BaseComponent<in T> {
  fun inject(target: T)
}
