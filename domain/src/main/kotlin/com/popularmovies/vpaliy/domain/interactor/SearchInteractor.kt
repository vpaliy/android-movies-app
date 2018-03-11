package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.executor.BaseScheduler
import com.popularmovies.vpaliy.domain.interactor.params.SearchPage
import com.popularmovies.vpaliy.domain.repository.SearchRepository
import com.popularmovies.vpaliy.domain.wrongArgument
import com.vpaliy.kotlin_extensions.then
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchInteractor<T> @Inject constructor(
    var repository: SearchRepository<T>,
    scheduler: BaseScheduler
) : SingleInteractor<SearchPage, List<T>>(scheduler) {

  override fun buildSingle(params: SearchPage?)
      = params then (repository::search) ?: wrongArgument()
}