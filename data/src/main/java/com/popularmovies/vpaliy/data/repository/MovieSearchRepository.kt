package com.popularmovies.vpaliy.data.repository

import com.popularmovies.vpaliy.domain.entity.Movie
import com.popularmovies.vpaliy.domain.interactor.params.MediaPage
import com.popularmovies.vpaliy.domain.interactor.params.Stream
import com.popularmovies.vpaliy.domain.repository.SearchRepository

class MovieSearchRepository:SearchRepository<Movie>{
    override fun search(page: MediaPage): Stream<MediaPage, List<Movie>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}