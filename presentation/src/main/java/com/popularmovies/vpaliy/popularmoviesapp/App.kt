package com.popularmovies.vpaliy.popularmoviesapp

import android.app.Application
import com.popularmovies.vpaliy.popularmoviesapp.di.component.ApplicationComponent
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerApplicationComponent
import com.popularmovies.vpaliy.popularmoviesapp.di.module.ApplicationModule
import com.popularmovies.vpaliy.popularmoviesapp.di.module.DataModule
import com.popularmovies.vpaliy.popularmoviesapp.di.module.InteractorModule
import com.popularmovies.vpaliy.popularmoviesapp.di.module.MapperModule

class App : Application() {
  val component: ApplicationComponent by lazy(LazyThreadSafetyMode.NONE) {
    DaggerApplicationComponent
        .builder()
        .applicationModule(ApplicationModule(this))
        .dataModule(DataModule())
        .interactorModule(InteractorModule())
        .mapperModule(MapperModule())
        .build()
  }

  override fun onCreate() {
    super.onCreate()
    instance = this
  }

  companion object {
    private var instance: App? = null
    val component by lazy(LazyThreadSafetyMode.NONE) {
      instance?.component
    }
  }
}
