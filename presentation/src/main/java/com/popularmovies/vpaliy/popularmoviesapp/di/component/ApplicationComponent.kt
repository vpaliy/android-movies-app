package com.popularmovies.vpaliy.popularmoviesapp.di.component

import android.content.Context
import com.popularmovies.vpaliy.domain.entity.Movie
import com.popularmovies.vpaliy.domain.entity.TVShow
import com.popularmovies.vpaliy.domain.repository.MediaRepository
import com.popularmovies.vpaliy.domain.repository.SearchRepository

import com.popularmovies.vpaliy.popularmoviesapp.di.module.ApplicationModule
import com.popularmovies.vpaliy.popularmoviesapp.di.module.DataModule
import com.popularmovies.vpaliy.popularmoviesapp.di.module.MapperModule

import javax.inject.Singleton
import dagger.Component

@Singleton
@Component(modules = arrayOf(ApplicationModule::class, DataModule::class, MapperModule::class))
interface ApplicationComponent {
    fun context(): Context
    fun movieRepository():MediaRepository<Movie>
    fun tvRepository():MediaRepository<TVShow>
    fun movieSearch():SearchRepository<Movie>
    fun tvSearch():SearchRepository<TVShow>
}
