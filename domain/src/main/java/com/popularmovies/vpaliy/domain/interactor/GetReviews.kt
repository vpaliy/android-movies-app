package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.entity.Review
import com.popularmovies.vpaliy.domain.executor.BaseScheduler
import com.popularmovies.vpaliy.domain.repository.MediaRepository
import com.popularmovies.vpaliy.domain.wrongArgument
import com.vpaliy.kotlin_extensions.then
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetReviews<T> @Inject constructor(val repository: MediaRepository<T>, scheduler: BaseScheduler)
  :GetDetail<List<Review>>(scheduler){

  override fun buildSingle(params: String?)=
          params then(repository::fetchReviews) ?: wrongArgument()
}