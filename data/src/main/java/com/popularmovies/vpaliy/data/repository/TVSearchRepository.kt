package com.popularmovies.vpaliy.data.repository

import com.popularmovies.vpaliy.data.entity.TVEntity
import com.popularmovies.vpaliy.data.mapper.Mapper
import com.popularmovies.vpaliy.domain.entity.TVShow
import com.popularmovies.vpaliy.domain.interactor.params.SearchPage
import com.popularmovies.vpaliy.domain.interactor.params.Stream
import com.popularmovies.vpaliy.domain.repository.SearchRepository
import com.popularmovies.vpaliy.domain.toStream
import com.vpaliy.tmdb.service.SearchService
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TVSearchRepository @Inject
constructor(val service:SearchService,val mapper:Mapper<TVShow,TVEntity>):SearchRepository<TVShow>{
    override fun search(page: SearchPage): Stream<SearchPage, List<TVShow>> {
        return Single.error<List<TVShow>>(IllegalArgumentException()).toStream(page)
    }
}