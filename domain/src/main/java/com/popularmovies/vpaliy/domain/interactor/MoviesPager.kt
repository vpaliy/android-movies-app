package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.entity.MediaSet
import com.popularmovies.vpaliy.domain.entity.Movie
import com.popularmovies.vpaliy.domain.executor.BaseScheduler
import com.popularmovies.vpaliy.domain.interactor.utils.Consumer
import com.popularmovies.vpaliy.domain.interactor.utils.TypePage
import com.popularmovies.vpaliy.domain.repository.Repository
import javax.inject.Inject

class MoviesPager @Inject
constructor(repository: Repository<Movie>, scheduler: BaseScheduler)
    :MediaInteractor<Movie,TypePage>(repository,scheduler){

    override fun fetchPage(consumer: Consumer<MediaSet<Movie>>, params: TypePage) {
        repository.fetchList(params)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe(consumer.success,consumer.error)
    }
}