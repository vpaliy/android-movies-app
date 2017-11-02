package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.error
import com.popularmovies.vpaliy.domain.executor.BaseSchedulerProvider
import com.popularmovies.vpaliy.domain.ifNotNull
import com.popularmovies.vpaliy.domain.interactor.params.TypePage
import com.popularmovies.vpaliy.domain.repository.MediaRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPage<T> @Inject
constructor(val repository: MediaRepository<T>, scheduler: BaseSchedulerProvider)
    :RequestInteractor<TypePage,List<T>>(scheduler){

    override fun buildUseCase(params: TypePage?)
            =params.ifNotNull(repository::fetchList, error())
}