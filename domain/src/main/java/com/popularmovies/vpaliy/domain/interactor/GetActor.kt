package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.entity.Actor
import com.popularmovies.vpaliy.domain.executor.BaseScheduler
import com.popularmovies.vpaliy.domain.repository.Repository
import io.reactivex.Single

class GetActor(var repository: Repository, scheduler: BaseScheduler)
    :SingleInteractor<Actor,String>(scheduler){

    override fun buildUseCase(params: String?): Single<Actor> {
        params?.let { return repository.fetchActor(it) }
        return Single.error(IllegalArgumentException())
    }
}