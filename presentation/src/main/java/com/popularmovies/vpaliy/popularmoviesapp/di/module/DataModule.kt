package com.popularmovies.vpaliy.popularmoviesapp.di.module

import com.popularmovies.vpaliy.data.Config
import com.popularmovies.vpaliy.data.repository.MovieRepository
import com.popularmovies.vpaliy.data.repository.MovieSearchRepository
import com.popularmovies.vpaliy.data.repository.TVRepository
import com.popularmovies.vpaliy.data.repository.TVSearchRepository
import com.popularmovies.vpaliy.domain.entity.Movie
import com.popularmovies.vpaliy.domain.entity.TVShow
import com.popularmovies.vpaliy.domain.repository.MediaRepository
import com.popularmovies.vpaliy.domain.repository.SearchRepository
import com.vpaliy.tmdb.TMDB
import com.vpaliy.tmdb.model.Genres
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule{

    private val client=TMDB(Config.API_KEY)

    @Provides
    @Singleton
    internal fun movieRepository(repository: MovieRepository):MediaRepository<Movie> =repository

    @Provides
    @Singleton
    internal fun tvRepository(repository:TVRepository):MediaRepository<TVShow> =repository

    @Provides
    @Singleton
    internal fun tvSearchRepository(repository:TVSearchRepository):SearchRepository<TVShow> =repository

    @Provides
    @Singleton
    internal fun movieSearchRepository(repository:MovieSearchRepository):SearchRepository<Movie> =repository

    @Provides
    @Singleton
    internal fun genreService()=client.genreService

    @Provides
    @Singleton
    internal fun moviesService()=client.moviesService
}
