package com.popularmovies.vpaliy.data.repository

import com.popularmovies.vpaliy.data.GenreKeeper
import com.popularmovies.vpaliy.data.entity.MovieEntity
import com.popularmovies.vpaliy.data.mapper.Mapper
import com.popularmovies.vpaliy.data.utils.filterOut
import com.popularmovies.vpaliy.domain.entity.Movie
import com.popularmovies.vpaliy.domain.interactor.params.SearchPage
import com.popularmovies.vpaliy.domain.interactor.params.Stream
import com.popularmovies.vpaliy.domain.repository.SearchRepository
import com.popularmovies.vpaliy.domain.toStream
import com.vpaliy.tmdb.service.SearchService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieSearchRepository @Inject
constructor(val service:SearchService,
            val mapper: Mapper<Movie,MovieEntity>,
            val genreKeeper: GenreKeeper):SearchRepository<Movie>{

    override fun search(page: SearchPage): Stream<SearchPage, List<Movie>> {
        return service.searchMovie(page.query){ query("page",page.current.toString()) }
                .map{it.results.filterOut()}
                .map{MovieEntity.build(it.toTypedArray(),genreKeeper)}
                .map(mapper::map).toStream(page)
    }
}