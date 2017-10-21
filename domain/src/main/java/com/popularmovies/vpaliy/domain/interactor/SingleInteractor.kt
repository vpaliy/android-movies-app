package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.executor.BaseSchedulerProvider
import com.popularmovies.vpaliy.domain.interactor.params.Consumer
import io.reactivex.Single

abstract class SingleInteractor<T,in Params>
constructor(scheduler: BaseSchedulerProvider):Interactor(scheduler){

    open fun execute(consumer: Consumer<T>, params: Params?=null)= {
        buildUseCase(params)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe(consumer.success, consumer.error)
    }

    protected abstract fun buildUseCase(params:Params?=null): Single<T>
}