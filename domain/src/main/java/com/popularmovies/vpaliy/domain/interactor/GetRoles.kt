package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.entity.Role
import com.popularmovies.vpaliy.domain.executor.BaseScheduler
import com.popularmovies.vpaliy.domain.repository.MediaRepository
import com.popularmovies.vpaliy.domain.then
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetRoles<T> @Inject constructor(val repository: MediaRepository<T>, scheduler: BaseScheduler)
    :GetDetail<List<Role>>(scheduler){

    override fun buildUseCase(params: String?): Single<List<Role>> {
        return params then(repository::fetchRoles)
                ?: Single.error(IllegalArgumentException())
    }
}