package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.entity.MediaSet
import com.popularmovies.vpaliy.domain.entity.TVShow
import com.popularmovies.vpaliy.domain.executor.BaseScheduler
import com.popularmovies.vpaliy.domain.interactor.utils.Consumer
import com.popularmovies.vpaliy.domain.interactor.utils.MediaPage
import com.popularmovies.vpaliy.domain.repository.Repository
import javax.inject.Inject

class TVPager @Inject
constructor(repository: Repository<TVShow>, scheduler: BaseScheduler)
    :MediaInteractor<TVShow,MediaPage>(repository,scheduler){

    override fun fetchPage(consumer: Consumer<MediaSet<TVShow>>, params: MediaPage) {
        repository.fetchList(params)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe(consumer.success,consumer.error)
    }
}