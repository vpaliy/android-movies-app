package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.entity.Actor
import com.popularmovies.vpaliy.domain.error
import com.popularmovies.vpaliy.domain.executor.BaseSchedulerProvider
import com.popularmovies.vpaliy.domain.repository.Repository
import com.popularmovies.vpaliy.domain.ifNotNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetActor @Inject constructor(var repository: Repository, scheduler: BaseSchedulerProvider)
    :RequestInteractor<String,Actor>(scheduler){

    override fun buildUseCase(params: String?)
            =params.ifNotNull(repository::fetchActor, error())
}