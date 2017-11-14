package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.entity.Review
import com.popularmovies.vpaliy.domain.executor.BaseScheduler
import com.popularmovies.vpaliy.domain.repository.MediaRepository
import com.popularmovies.vpaliy.domain.then
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetReviews<T> @Inject constructor(val repository: MediaRepository<T>, scheduler: BaseScheduler)
    :GetDetail<List<Review>>(scheduler){

    override fun buildUseCase(params: String?): Single<List<Review>> {
        return params then(repository::fetchReviews)
                ?: Single.error(IllegalArgumentException())
    }
}