package com.popularmovies.vpaliy.popularmoviesapp.di.module

import android.app.Application
import android.content.Context
import com.popularmovies.vpaliy.domain.executor.BaseScheduler
import com.popularmovies.vpaliy.domain.executor.SchedulerProvider
import javax.inject.Singleton
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val application: Application) {
    @Singleton
    @Provides
    internal fun context():Context = application

    @Singleton
    @Provides
    internal fun scheduler():BaseScheduler=SchedulerProvider()
}
