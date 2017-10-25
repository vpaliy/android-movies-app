package com.popularmovies.vpaliy.popularmoviesapp

import android.support.multidex.MultiDexApplication
import com.popularmovies.vpaliy.popularmoviesapp.di.component.ApplicationComponent

class App : MultiDexApplication() {

    private val applicationComponent: ApplicationComponent? = null

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        Melophile.
    }

    fun appComponent(): ApplicationComponent {
        return applicationComponent
    }

    companion object {
        private var INSTANCE: App? = null

        fun appInstance(): App {
            return INSTANCE
        }
    }

}
