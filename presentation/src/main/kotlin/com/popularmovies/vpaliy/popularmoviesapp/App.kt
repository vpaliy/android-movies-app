package com.popularmovies.vpaliy.popularmoviesapp

import android.app.Application
import com.popularmovies.vpaliy.domain.entity.Popular
import com.popularmovies.vpaliy.popularmoviesapp.di.component.ApplicationComponent
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerApplicationComponent
import com.popularmovies.vpaliy.popularmoviesapp.di.injector.InjectorStack
import com.popularmovies.vpaliy.popularmoviesapp.di.module.ApplicationModule
import com.popularmovies.vpaliy.popularmoviesapp.di.module.DataModule
import com.popularmovies.vpaliy.popularmoviesapp.di.module.InteractorModule
import com.popularmovies.vpaliy.popularmoviesapp.di.module.MapperModule
import com.popularmovies.vpaliy.popularmoviesapp.di.injector.Injector
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseView
import com.popularmovies.vpaliy.popularmoviesapp.ui.more.MoreActivity

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

  val injectorStack by lazy(LazyThreadSafetyMode.NONE) {
    InjectorStack()
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

    fun <T : BaseView> inject(target: T) {
      instance?.injectorStack?.top()?.inject(target)
    }

    fun <T : BaseView> release(target: T) {
      instance?.injectorStack?.pop()
    }

    fun pushInjector(injector: Injector<*>) {
      instance?.injectorStack?.push(injector)
    }
  }
}
