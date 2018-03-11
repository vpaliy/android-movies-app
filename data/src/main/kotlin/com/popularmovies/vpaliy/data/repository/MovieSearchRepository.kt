package com.popularmovies.vpaliy.data.repository

import com.popularmovies.vpaliy.data.GenreKeeper
import com.popularmovies.vpaliy.data.entity.MovieEntity
import com.popularmovies.vpaliy.data.mapper.Mapper
import com.popularmovies.vpaliy.data.utils.filterOut
import com.popularmovies.vpaliy.domain.entity.Movie
import com.popularmovies.vpaliy.domain.interactor.params.SearchPage
import com.popularmovies.vpaliy.domain.repository.SearchRepository
import com.vpaliy.tmdb.adapter.SearchServiceAdapter
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieSearchRepository @Inject constructor(
    private val service: SearchServiceAdapter,
    private val mapper: Mapper<Movie, MovieEntity>,
    private val genreKeeper: GenreKeeper
) : SearchRepository<Movie> {

  override fun search(page: SearchPage): Single<List<Movie>> {
    return service.searchMovie(page.query) { query("page", page.current.toString()) }
        .map { it.results.filterOut() }
        .map { MovieEntity.build(it.toTypedArray(), genreKeeper) }
        .map(mapper::map)
  }

}