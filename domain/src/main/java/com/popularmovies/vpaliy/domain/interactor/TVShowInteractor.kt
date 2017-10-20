package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.entity.TVShow
import com.popularmovies.vpaliy.domain.executor.BaseScheduler
import com.popularmovies.vpaliy.domain.repository.PersonalRepository
import javax.inject.Inject

class TVShowInteractor @Inject
constructor(repository: PersonalRepository<TVShow>,scheduler: BaseScheduler)
    :PersonalInteractor<TVShow>(repository,scheduler)