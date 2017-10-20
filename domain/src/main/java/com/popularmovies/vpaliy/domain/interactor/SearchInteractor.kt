package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.executor.BaseScheduler
import com.popularmovies.vpaliy.domain.interactor.utils.Consumer
import com.popularmovies.vpaliy.domain.repository.SearchRepository

abstract class SearchInteractor<out T>
constructor(private val repository:SearchRepository<T>,
            private val scheduler: BaseScheduler){

    fun query(consumer: Consumer<List<T>>, query:String){
        repository.query(query)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe(consumer.success,consumer.error)
    }

    fun more(consumer: Consumer<List<T>>){
        repository.next()
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe(consumer.success,consumer.error)
    }
}