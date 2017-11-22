package com.popularmovies.vpaliy.popularmoviesapp.di.component

import android.content.Context
import com.popularmovies.vpaliy.data.mapper.Mapper
import com.popularmovies.vpaliy.domain.entity.Actor
import com.popularmovies.vpaliy.domain.entity.Movie
import com.popularmovies.vpaliy.domain.entity.TVShow
import com.popularmovies.vpaliy.domain.executor.BaseScheduler
import com.popularmovies.vpaliy.domain.interactor.*
import com.popularmovies.vpaliy.domain.repository.MediaRepository
import com.popularmovies.vpaliy.popularmoviesapp.di.module.ApplicationModule
import com.popularmovies.vpaliy.popularmoviesapp.di.module.DataModule
import com.popularmovies.vpaliy.popularmoviesapp.di.module.InteractorModule
import com.popularmovies.vpaliy.popularmoviesapp.di.module.MapperModule
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseActivity
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.Navigator
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
    fun navigator():Navigator
    fun movieRepository():MediaRepository<Movie>
    fun tvRepository():MediaRepository<TVShow>
    fun movieSearch():SearchInteractor<Movie>
    fun tvSearch():SearchInteractor<TVShow>
    fun peopleSearch():SearchInteractor<Actor>
    fun moviesInteractor():GetPage<Movie>
    fun tvInteractor():GetPage<TVShow>
    fun movieReviews():GetReviews<Movie>
    fun movieTrailers():GetTrailers<Movie>
    fun suggestions():GetSuggestion<Movie>
    fun movieRoles():GetRoles<Movie>
    fun movieItem():GetItem<Movie>
    fun mapper():Mapper<MediaModel,Movie>
    fun tv():Mapper<MediaModel,TVShow>
}
