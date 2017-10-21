package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.entity.Review
import com.popularmovies.vpaliy.domain.executor.BaseSchedulerProvider
import com.popularmovies.vpaliy.domain.repository.MediaRepository
import com.popularmovies.vpaliy.domain.ifNotNull
import io.reactivex.Single
import javax.inject.Inject

class GetReviews<T> @Inject
constructor(val repository: MediaRepository<T>, scheduler: BaseSchedulerProvider)
    :SingleInteractor<List<Review>,T>(scheduler){

    override fun buildUseCase(params: T?): Single<List<Review>> {
        return params.ifNotNull(repository::fetchReviews,
                Single.error(IllegalArgumentException()))
    }
}