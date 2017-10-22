package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.entity.Role
import com.popularmovies.vpaliy.domain.error
import com.popularmovies.vpaliy.domain.executor.BaseSchedulerProvider
import com.popularmovies.vpaliy.domain.ifNotNull
import com.popularmovies.vpaliy.domain.repository.MediaRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetRoles<Params> @Inject
constructor(val repository: MediaRepository<Params>, scheduler: BaseSchedulerProvider)
    : RequestInteractor<Params,List<Role>>(scheduler){

    override fun buildUseCase(params: Params?)
            =params.ifNotNull(repository::fetchRoles,error())
}