package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.error
import com.popularmovies.vpaliy.domain.executor.BaseSchedulerProvider
import com.popularmovies.vpaliy.domain.ifNotNull
import com.popularmovies.vpaliy.domain.interactor.params.MediaPage
import com.popularmovies.vpaliy.domain.repository.SearchRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchInteractor<T> @Inject
constructor(var repository: SearchRepository<T>,scheduler: BaseSchedulerProvider)
    :RequestInteractor<MediaPage,List<T>>(scheduler){

    override fun buildUseCase(params: MediaPage?)
            =params.ifNotNull(repository::search, error())
}