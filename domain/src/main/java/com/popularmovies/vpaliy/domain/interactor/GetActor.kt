package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.entity.Actor
import com.popularmovies.vpaliy.domain.error
import com.popularmovies.vpaliy.domain.executor.BaseScheduler
import com.popularmovies.vpaliy.domain.repository.Repository
import com.popularmovies.vpaliy.domain.then
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetActor @Inject constructor(var repository: Repository, scheduler: BaseScheduler)
    :RequestInteractor<String,Actor>(scheduler){

    override fun buildUseCase(params: String?)
            =params then(repository::fetchActor)?:error()
}