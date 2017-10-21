package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.entity.Role
import com.popularmovies.vpaliy.domain.executor.BaseScheduler
import com.popularmovies.vpaliy.domain.repository.MediaRepository
import io.reactivex.Single
import javax.inject.Inject

class GetRoles<T> @Inject
constructor(val repository: MediaRepository<T>, scheduler: BaseScheduler)
    :SingleInteractor<List<Role>,T>(scheduler){

    override fun buildUseCase(params: T?): Single<List<Role>> {
        params?.let { return repository.fetchRoles(params) }
        return Single.error(IllegalArgumentException())
    }
}