package com.popularmovies.vpaliy.popularmoviesapp.di.injector


class InjectorStack {
  private val injectors = arrayListOf<Injector<*>>()

  fun top() = injectors.last()

  fun pop() = injectors.last()

  fun push(injector: Injector<*>) {
    injectors.add(injector)
  }
}