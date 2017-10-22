package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.entity.Review
import com.popularmovies.vpaliy.domain.error
import com.popularmovies.vpaliy.domain.executor.BaseSchedulerProvider
import com.popularmovies.vpaliy.domain.repository.MediaRepository
import com.popularmovies.vpaliy.domain.ifNotNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetReviews<Request> @Inject
constructor(val repository: MediaRepository<Request>, scheduler: BaseSchedulerProvider)
    : RequestInteractor<Request,List<Review>>(scheduler){

    override fun buildUseCase(params: Request?)
            =params.ifNotNull(repository::fetchReviews, error())
}