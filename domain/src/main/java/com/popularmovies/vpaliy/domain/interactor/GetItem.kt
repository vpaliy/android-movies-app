package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.error
import com.popularmovies.vpaliy.domain.executor.BaseSchedulerProvider
import com.popularmovies.vpaliy.domain.ifNotNull
import com.popularmovies.vpaliy.domain.repository.MediaRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetItem<T> @Inject
constructor(val repository: MediaRepository<T>, scheduler: BaseSchedulerProvider)
        :RequestInteractor<String,T>(scheduler){

    override fun buildUseCase(params: String?)
            =params.ifNotNull(repository::fetchItem,error())
}