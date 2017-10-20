package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.entity.TVShow
import com.popularmovies.vpaliy.domain.executor.BaseScheduler
import com.popularmovies.vpaliy.domain.repository.SearchRepository
import javax.inject.Inject

class SearchTVShows @Inject
constructor(repository: SearchRepository<TVShow>, scheduler: BaseScheduler)
    :SearchInteractor<TVShow>(repository,scheduler)