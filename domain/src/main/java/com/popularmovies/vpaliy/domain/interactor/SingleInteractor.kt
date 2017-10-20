package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.executor.BaseScheduler
import com.popularmovies.vpaliy.domain.interactor.utils.Consumer
import rx.Single

abstract class SingleInteractor<T,in Params>
constructor(val scheduler: BaseScheduler){

    open fun execute(consumer: Consumer<T>, params: Params?=null)= buildObservable(params)
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
            .subscribe(consumer.success,consumer.error)

    protected abstract fun buildObservable(params:Params?=null): Single<T>
}