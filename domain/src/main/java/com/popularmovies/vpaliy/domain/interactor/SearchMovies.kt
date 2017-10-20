package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.entity.Movie
import com.popularmovies.vpaliy.domain.executor.BaseScheduler
import com.popularmovies.vpaliy.domain.repository.SearchRepository
import javax.inject.Inject

class SearchMovies @Inject
constructor(repository: SearchRepository<Movie>, scheduler: BaseScheduler)
    :SearchInteractor<Movie>(repository,scheduler)