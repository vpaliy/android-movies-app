package com.popularmovies.vpaliy.data.repository

import com.popularmovies.vpaliy.domain.entity.Movie
import com.popularmovies.vpaliy.domain.repository.SearchRepository
import rx.Single

class MovieSearchRepository:SearchRepository<Movie>{
    override fun query(query: String): Single<List<Movie>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun next(): Single<List<Movie>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}