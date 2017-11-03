package com.popularmovies.vpaliy.popularmoviesapp.di.module

import com.popularmovies.vpaliy.domain.entity.Movie
import com.popularmovies.vpaliy.domain.entity.TVShow
import com.popularmovies.vpaliy.domain.executor.BaseScheduler
import com.popularmovies.vpaliy.domain.interactor.GetPage
import com.popularmovies.vpaliy.domain.interactor.SearchInteractor
import com.popularmovies.vpaliy.domain.repository.MediaRepository
import com.popularmovies.vpaliy.domain.repository.SearchRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class InteractorModule{
    @Singleton
    @Provides
    internal fun movies(repository:MediaRepository<Movie>, scheduler: BaseScheduler)
            :GetPage<Movie> =GetPage(repository,scheduler)

    @Singleton
    @Provides
    internal fun tv(repository: MediaRepository<TVShow>, scheduler: BaseScheduler)
        :GetPage<TVShow> = GetPage(repository,scheduler)

    @Singleton
    @Provides
    internal fun searchMovies(repository: SearchRepository<Movie>,scheduler: BaseScheduler)
            =SearchInteractor(repository,scheduler)

    @Singleton
    @Provides
    internal fun searchTV(repository: SearchRepository<TVShow>,scheduler: BaseScheduler)
            =SearchInteractor(repository,scheduler)
}