package com.popularmovies.vpaliy.popularmoviesapp.di.module

import android.app.Application
import android.content.Context
import javax.inject.Singleton
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val application: Application) {
    @Singleton
    @Provides
    fun context():Context = application
}
