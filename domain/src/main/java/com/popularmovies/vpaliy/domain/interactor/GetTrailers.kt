package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.entity.Trailer
import com.popularmovies.vpaliy.domain.executor.BaseScheduler
import com.popularmovies.vpaliy.domain.repository.MediaRepository
import io.reactivex.Single
import javax.inject.Inject

class GetTrailers<T> @Inject
constructor(val repository: MediaRepository<T>, scheduler: BaseScheduler)
    :SingleInteractor<List<Trailer>,T>(scheduler){

    override fun buildUseCase(params: T?): Single<List<Trailer>> {
        params?.let {
            return repository.fetchTrailers(params)
        }
        return Single.error(IllegalArgumentException())
    }
}