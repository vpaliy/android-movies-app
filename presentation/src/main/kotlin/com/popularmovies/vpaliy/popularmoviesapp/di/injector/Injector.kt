package com.popularmovies.vpaliy.popularmoviesapp.di.injector

import com.popularmovies.vpaliy.popularmoviesapp.di.component.BaseComponent

interface Injector<in T> {
  val component: BaseComponent<T>

  fun inject(target: Any)
}