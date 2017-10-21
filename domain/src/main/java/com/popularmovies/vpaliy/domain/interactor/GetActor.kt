package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.entity.Actor
import com.popularmovies.vpaliy.domain.executor.BaseSchedulerProvider
import com.popularmovies.vpaliy.domain.repository.Repository
import com.popularmovies.vpaliy.domain.ifNotNull
import io.reactivex.Single
import javax.inject.Inject

class GetActor @Inject
constructor(var repository: Repository, scheduler: BaseSchedulerProvider)
    :SingleInteractor<Actor,String>(scheduler){

    override fun buildUseCase(params: String?): Single<Actor> {
        return params.ifNotNull(repository::fetchActor,
                Single.error(IllegalArgumentException()))
    }
}