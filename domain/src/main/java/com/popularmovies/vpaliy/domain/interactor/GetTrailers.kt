package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.entity.Trailer
import com.popularmovies.vpaliy.domain.error
import com.popularmovies.vpaliy.domain.executor.BaseScheduler
import com.popularmovies.vpaliy.domain.ifNotNull
import com.popularmovies.vpaliy.domain.repository.MediaRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTrailers<Request> @Inject
constructor(val repository: MediaRepository<Request>, scheduler: BaseScheduler)
    : RequestInteractor<Request, List<Trailer>>(scheduler){

    override fun buildUseCase(params: Request?)
            =params.ifNotNull(repository::fetchTrailers,error())
}