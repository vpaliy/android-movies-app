package com.popularmovies.vpaliy.data.repository

import com.popularmovies.vpaliy.data.GenreKeeper
import com.popularmovies.vpaliy.data.entity.TVEntity
import com.popularmovies.vpaliy.data.mapper.Mapper
import com.popularmovies.vpaliy.data.utils.filterOut
import com.popularmovies.vpaliy.domain.entity.TVShow
import com.popularmovies.vpaliy.domain.interactor.params.SearchPage
import com.popularmovies.vpaliy.domain.repository.SearchRepository
import com.vpaliy.tmdb.service.SearchService
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TVSearchRepository @Inject constructor(val service:SearchService,
                                             val genreKeeper: GenreKeeper,
                                             val mapper:Mapper<TVShow,TVEntity>):SearchRepository<TVShow>{
    override fun search(page: SearchPage): Single<List<TVShow>> {
        return service.searchTV(page.query){
            query("page",page.current.toString())
        }.map{it.results.filterOut()}
                .map{TVEntity.build(it.toTypedArray(),genreKeeper)}
                .map(mapper::map)
    }
}