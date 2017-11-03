package com.popularmovies.vpaliy.popularmoviesapp.di.component

import android.content.Context
import com.popularmovies.vpaliy.data.GenreKeeper
import com.popularmovies.vpaliy.data.mapper.Mapper
import com.popularmovies.vpaliy.domain.entity.Movie
import com.popularmovies.vpaliy.domain.entity.TVShow
import com.popularmovies.vpaliy.domain.executor.BaseScheduler
import com.popularmovies.vpaliy.domain.interactor.GetPage
import com.popularmovies.vpaliy.domain.repository.MediaRepository
import com.popularmovies.vpaliy.domain.repository.SearchRepository
import com.popularmovies.vpaliy.popularmoviesapp.di.module.ApplicationModule
import com.popularmovies.vpaliy.popularmoviesapp.di.module.DataModule
import com.popularmovies.vpaliy.popularmoviesapp.di.module.InteractorModule
import com.popularmovies.vpaliy.popularmoviesapp.di.module.MapperModule
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseActivity
import com.popularmovies.vpaliy.popularmoviesapp.ui.mapper.MediaMovieMapper
import com.popularmovies.vpaliy.popularmoviesapp.ui.mapper.MediaTVMapper
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaModel
import javax.inject.Singleton
import dagger.Component

@Singleton
@Component(modules = arrayOf(ApplicationModule::class, DataModule::class,
        MapperModule::class,InteractorModule::class))
interface ApplicationComponent {
    fun inject(activity:BaseActivity)
    fun context(): Context
    fun scheduler():BaseScheduler
    fun movieRepository():MediaRepository<Movie>
    fun tvRepository():MediaRepository<TVShow>
    fun movieSearch():SearchRepository<Movie>
    fun tvSearch():SearchRepository<TVShow>
    fun moviesInteractor():GetPage<Movie>
    fun tvInteractor():GetPage<TVShow>
    fun mapper():Mapper<MediaModel,Movie>
    fun tv():Mapper<MediaModel,TVShow>
}
