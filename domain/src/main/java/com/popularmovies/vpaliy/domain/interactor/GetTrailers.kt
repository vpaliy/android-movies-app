package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.entity.Trailer
import com.popularmovies.vpaliy.domain.executor.BaseSchedulerProvider
import com.popularmovies.vpaliy.domain.ifNotNull
import com.popularmovies.vpaliy.domain.repository.MediaRepository
import io.reactivex.Single
import javax.inject.Inject

class GetTrailers<T> @Inject
constructor(val repository: MediaRepository<T>, scheduler: BaseSchedulerProvider)
    :SingleInteractor<List<Trailer>,T>(scheduler){

    override fun buildUseCase(params: T?): Single<List<Trailer>> {
        return params.ifNotNull(repository::fetchTrailers,
                Single.error(IllegalArgumentException()))
    }
}