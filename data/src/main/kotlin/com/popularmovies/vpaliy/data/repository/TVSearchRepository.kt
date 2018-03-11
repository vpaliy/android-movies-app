package com.popularmovies.vpaliy.data.repository

import com.popularmovies.vpaliy.data.GenreKeeper
import com.popularmovies.vpaliy.data.entity.TVEntity
import com.popularmovies.vpaliy.data.mapper.Mapper
import com.popularmovies.vpaliy.data.utils.filterOut
import com.popularmovies.vpaliy.domain.entity.TVShow
import com.popularmovies.vpaliy.domain.interactor.params.SearchPage
import com.popularmovies.vpaliy.domain.repository.SearchRepository
import com.vpaliy.tmdb.adapter.SearchServiceAdapter
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TVSearchRepository @Inject constructor(
    private val service: SearchServiceAdapter,
    private val genreKeeper: GenreKeeper,
    private val mapper: Mapper<TVShow, TVEntity>
) : SearchRepository<TVShow> {

  override fun search(page: SearchPage): Single<List<TVShow>> {
    return service.searchTV(page.query) {
      query("page", page.current.toString())
    }.map { it.results.filterOut() }
        .map { TVEntity.build(it.toTypedArray(), genreKeeper) }
        .map(mapper::map)
  }
}