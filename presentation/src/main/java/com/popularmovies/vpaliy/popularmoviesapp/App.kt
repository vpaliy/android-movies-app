package com.popularmovies.vpaliy.popularmoviesapp

import android.app.Application
import com.popularmovies.vpaliy.popularmoviesapp.di.component.ApplicationComponent
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerApplicationComponent
import com.popularmovies.vpaliy.popularmoviesapp.di.module.ApplicationModule

class App : Application() {

    val component:ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        instance=this
    }

    companion object {
        private var instance:App?=null
        val component by lazy {
            instance?.component
        }
    }
}
