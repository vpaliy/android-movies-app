package com.popularmovies.vpaliy.data.repository

import com.popularmovies.vpaliy.domain.entity.Actor
import com.popularmovies.vpaliy.domain.interactor.params.SearchPage
import com.popularmovies.vpaliy.domain.interactor.params.Stream
import com.popularmovies.vpaliy.domain.repository.SearchRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PeopleSearchRepository @Inject constructor():SearchRepository<Actor>{
    override fun search(page: SearchPage): Stream<SearchPage, List<Actor>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}