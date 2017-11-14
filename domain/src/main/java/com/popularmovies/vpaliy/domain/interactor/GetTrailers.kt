package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.entity.Trailer
import com.popularmovies.vpaliy.domain.executor.BaseScheduler
import com.popularmovies.vpaliy.domain.repository.MediaRepository
import com.popularmovies.vpaliy.domain.then
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTrailers<T> @Inject constructor(val repository: MediaRepository<T>, scheduler: BaseScheduler)
        :SingleInteractor<String, List<Trailer>>(scheduler){

    override fun buildUseCase(params: String?): Single<List<Trailer>> {
        return params then(repository::fetchTrailers)
                ?:Single.error(IllegalArgumentException())
    }
}