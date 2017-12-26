package com.popularmovies.vpaliy.data.repository

import com.popularmovies.vpaliy.data.mapper.Mapper
import com.popularmovies.vpaliy.data.utils.filterOut
import com.popularmovies.vpaliy.domain.entity.Actor
import com.popularmovies.vpaliy.domain.interactor.params.SearchPage
import com.popularmovies.vpaliy.domain.repository.Repository
import com.popularmovies.vpaliy.domain.repository.SearchRepository
import com.vpaliy.tmdb.model.ActorModel
import com.vpaliy.tmdb.service.SearchService
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PeopleRepository @Inject
constructor(private val service: SearchService, private val mapper: Mapper<Actor, ActorModel>) : Repository, SearchRepository<Actor> {

  override fun search(page: SearchPage): Single<List<Actor>> {
    return service.searchPeople(page.query) {
      query("page", page.current.toString())
    }.map { mapper.map(it.results.filterOut()) }
  }

  override fun fetchActor(id: String): Single<Actor> {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}