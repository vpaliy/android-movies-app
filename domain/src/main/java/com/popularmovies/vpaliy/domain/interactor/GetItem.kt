package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.error
import com.popularmovies.vpaliy.domain.executor.BaseScheduler
import com.popularmovies.vpaliy.domain.ifNotNull
import com.popularmovies.vpaliy.domain.repository.MediaRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetItem<T> @Inject
constructor(val repository: MediaRepository<T>, scheduler: BaseScheduler)
        :RequestInteractor<String,T>(scheduler){

    override fun buildUseCase(params: String?)
            =params.ifNotNull(repository::fetchItem,error())
}