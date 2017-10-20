package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.entity.Trailer
import com.popularmovies.vpaliy.domain.executor.BaseScheduler
import com.popularmovies.vpaliy.domain.repository.Repository
import rx.Single
import javax.inject.Inject

class GetTrailers<T> @Inject
constructor(val repository: Repository<T>, scheduler: BaseScheduler)
    :SingleInteractor<List<Trailer>,T>(scheduler){

    override fun buildObservable(params: T?): Single<List<Trailer>> {
        params?.let {
            return repository.fetchTrailers(params)
        }
        return Single.create {  }
    }
}