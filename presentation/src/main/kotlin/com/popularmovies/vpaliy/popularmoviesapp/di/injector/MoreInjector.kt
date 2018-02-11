package com.popularmovies.vpaliy.popularmoviesapp.di.injector

import com.popularmovies.vpaliy.popularmoviesapp.App
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerSearchComponent
import com.popularmovies.vpaliy.popularmoviesapp.di.module.SearchModule
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.Injector
import com.popularmovies.vpaliy.popularmoviesapp.ui.more.MoreActivity
import okhttp3.MediaType

class MoreInjector(private val type: MediaType) : Injector<MoreActivity> {

  private val component by lazy {
    DaggerSearchComponent.builder()
        .applicationComponent(App.component)
        .searchModule(SearchModule())
        .build()
  }

  override fun inject(target: MoreActivity) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun release() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}