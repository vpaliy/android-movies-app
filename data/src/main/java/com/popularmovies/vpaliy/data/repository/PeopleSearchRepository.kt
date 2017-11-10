package com.popularmovies.vpaliy.data.repository

import com.popularmovies.vpaliy.data.mapper.Mapper
import com.popularmovies.vpaliy.domain.entity.Actor
import com.popularmovies.vpaliy.domain.interactor.params.SearchPage
import com.popularmovies.vpaliy.domain.interactor.params.Stream
import com.popularmovies.vpaliy.domain.repository.SearchRepository
import com.popularmovies.vpaliy.domain.toStream
import com.vpaliy.tmdb.model.ActorModel
import com.vpaliy.tmdb.service.SearchService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PeopleSearchRepository @Inject
constructor(private val service:SearchService,
            private val mapper: Mapper<Actor, ActorModel>):SearchRepository<Actor>{
    override fun search(page: SearchPage): Stream<SearchPage, List<Actor>> {
        return service.searchPeople(page.query){
                    query("page",page.current.toString())
                }
                .map {mapper.map(it.results.toList())}
                .toStream(page)
    }
}