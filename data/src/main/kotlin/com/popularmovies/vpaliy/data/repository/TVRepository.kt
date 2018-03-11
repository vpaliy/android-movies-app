package com.popularmovies.vpaliy.data.repository

import com.popularmovies.vpaliy.data.GenreKeeper
import com.popularmovies.vpaliy.data.buildQuery
import com.popularmovies.vpaliy.data.entity.TVEntity
import com.popularmovies.vpaliy.data.mapper.Mapper
import com.popularmovies.vpaliy.data.utils.filterOut
import com.popularmovies.vpaliy.domain.entity.*
import com.popularmovies.vpaliy.domain.interactor.params.Suggestion
import com.popularmovies.vpaliy.domain.interactor.params.TypePage
import com.popularmovies.vpaliy.domain.repository.MediaRepository
import com.vpaliy.tmdb.adapter.TVServiceAdapter
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TVRepository @Inject constructor(
    private val mapper: Mapper<TVShow, TVEntity>,
    private val genreKeeper: GenreKeeper,
    private val service: TVServiceAdapter
) : MediaRepository<TVShow> {

  override fun fetchList(request: TypePage): Single<List<TVShow>> {
    val result = when (request.type) {
      Popular -> service.getPopular(request.buildQuery())
      Top -> service.getTvOnAir(request.buildQuery())
      Upcoming -> service.getAiringToday(request.buildQuery())
      else -> service.getAiringToday(request.buildQuery())
    }
    return result
        .map { it.results.filterOut() }
        .map { TVEntity.build(it.toTypedArray(), genreKeeper) }
        .map(mapper::map)
  }

  override fun fetchReviews(id: String): Single<List<Review>> {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun fetchRoles(id: String): Single<List<Role>> {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun fetchSuggested(request: Suggestion): Single<List<TVShow>> {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun fetchTrailers(id: String): Single<List<Trailer>> {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun fetchItem(id: String): Single<TVShow> {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}