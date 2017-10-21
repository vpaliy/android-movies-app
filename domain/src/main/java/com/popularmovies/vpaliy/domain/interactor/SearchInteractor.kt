package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.entity.MediaSet
import com.popularmovies.vpaliy.domain.executor.BaseSchedulerProvider
import com.popularmovies.vpaliy.domain.interactor.params.Consumer
import com.popularmovies.vpaliy.domain.interactor.params.MediaPage
import com.popularmovies.vpaliy.domain.repository.SearchRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchInteractor<T> @Inject
constructor(var repository: SearchRepository<T>,scheduler: BaseSchedulerProvider)
    :PagerInteractor<T,MediaPage>(scheduler){

    override fun fetchPage(consumer: Consumer<MediaSet<T>>, params: MediaPage) {
        repository.search(params)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
    }
}