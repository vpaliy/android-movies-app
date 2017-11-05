package com.popularmovies.vpaliy.data.repository

import com.popularmovies.vpaliy.domain.entity.Movie
import com.popularmovies.vpaliy.domain.interactor.params.MediaPage
import com.popularmovies.vpaliy.domain.interactor.params.SearchPage
import com.popularmovies.vpaliy.domain.interactor.params.Stream
import com.popularmovies.vpaliy.domain.repository.SearchRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieSearchRepository @Inject constructor():SearchRepository<Movie>{

    override fun search(page: SearchPage): Stream<SearchPage, List<Movie>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}