package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.entity.Role
import com.popularmovies.vpaliy.domain.executor.BaseScheduler
import com.popularmovies.vpaliy.domain.repository.Repository
import rx.Single
import javax.inject.Inject

class GetRoles<T> @Inject
constructor(val repository: Repository<T>, scheduler: BaseScheduler)
    :SingleInteractor<List<Role>,T>(scheduler){
    override fun buildObservable(params: T?): Single<List<Role>> {
        params?.let {
            return repository.fetchRoles(params)
        }
        return Single.create {  }
    }
}