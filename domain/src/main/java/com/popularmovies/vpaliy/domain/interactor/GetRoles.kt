package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.entity.Role
import com.popularmovies.vpaliy.domain.executor.BaseSchedulerProvider
import com.popularmovies.vpaliy.domain.ifNotNull
import com.popularmovies.vpaliy.domain.repository.MediaRepository
import io.reactivex.Single
import javax.inject.Inject

class GetRoles<T> @Inject
constructor(val repository: MediaRepository<T>, scheduler: BaseSchedulerProvider)
    :SingleInteractor<List<Role>,T>(scheduler){

    override fun buildUseCase(params: T?): Single<List<Role>> {
        return params.ifNotNull(repository::fetchRoles,
                Single.error(IllegalArgumentException()))
    }
}