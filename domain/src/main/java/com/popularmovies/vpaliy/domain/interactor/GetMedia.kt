package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.entity.MediaSet
import com.popularmovies.vpaliy.domain.executor.BaseSchedulerProvider
import com.popularmovies.vpaliy.domain.interactor.params.Consumer
import com.popularmovies.vpaliy.domain.interactor.params.MediaPage
import com.popularmovies.vpaliy.domain.repository.MediaRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetMedia<T> @Inject
constructor(repository: MediaRepository<T>,scheduler: BaseSchedulerProvider)
    :MediaInteractor<T,MediaPage>(repository,scheduler){

    override fun fetchPage(consumer: Consumer<MediaSet<T>>, params: MediaPage) {
        repository.fetchList(params)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe(consumer.success,consumer.error)
    }
}