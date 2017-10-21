package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.entity.Review
import com.popularmovies.vpaliy.domain.executor.BaseScheduler
import com.popularmovies.vpaliy.domain.repository.MediaRepository
import io.reactivex.Single
import javax.inject.Inject

class GetReviews<T> @Inject
constructor(val repository: MediaRepository<T>, scheduler: BaseScheduler)
    :SingleInteractor<List<Review>,T>(scheduler){

    override fun buildUseCase(params: T?): Single<List<Review>> {
        params?.let {
            return repository.fetchReviews(params)
        }
        return Single.error(IllegalArgumentException())
    }
}