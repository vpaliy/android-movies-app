package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.entity.Review
import com.popularmovies.vpaliy.domain.executor.BaseScheduler
import com.popularmovies.vpaliy.domain.repository.Repository
import io.reactivex.Single
import javax.inject.Inject

class GetReviews<T> @Inject
constructor(val repository: Repository<T>, scheduler: BaseScheduler)
    :SingleInteractor<List<Review>,T>(scheduler){

    override fun buildObservable(params: T?): Single<List<Review>> {
        params?.let {
            return repository.fetchReviews(params)
        }
        return Single.error(IllegalArgumentException())
    }
}