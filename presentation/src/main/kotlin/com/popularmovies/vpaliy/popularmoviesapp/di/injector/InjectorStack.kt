package com.popularmovies.vpaliy.popularmoviesapp.di.injector

import com.popularmovies.vpaliy.popularmoviesapp.ui.base.Injector

class InjectorStack {
  private val injectors = mutableListOf<Injector<Any>>()

  fun top() = injectors.last()

  fun pop() = injectors.last()

  fun<T:Any> push(injector: Injector<T>) = apply {
    injectors.add(injector)
  }
}