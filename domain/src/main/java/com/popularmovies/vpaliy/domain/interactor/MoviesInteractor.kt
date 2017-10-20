package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.entity.Movie
import com.popularmovies.vpaliy.domain.executor.BaseScheduler
import com.popularmovies.vpaliy.domain.repository.PersonalRepository
import javax.inject.Inject

class MoviesInteractor @Inject
constructor(repository: PersonalRepository<Movie>,scheduler: BaseScheduler)
    :PersonalInteractor<Movie>(repository,scheduler)