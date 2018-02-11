package com.popularmovies.vpaliy.popularmoviesapp.ui.base

interface Injector<T> {
  fun inject(target: T)
  fun release()
}