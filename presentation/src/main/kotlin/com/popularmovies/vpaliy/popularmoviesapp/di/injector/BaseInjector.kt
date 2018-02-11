package com.popularmovies.vpaliy.popularmoviesapp.di.injector

import com.popularmovies.vpaliy.popularmoviesapp.App
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.Injector

abstract class BaseInjector<T> : Injector<T> {
  val component by lazy {
    App.component
  }
}