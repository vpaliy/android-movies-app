package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.executor.BaseScheduler
import com.popularmovies.vpaliy.domain.interactor.params.Consumer
import com.popularmovies.vpaliy.domain.interactor.params.Stream

abstract class RequestInteractor<Request,Result>
constructor(scheduler: BaseScheduler):Interactor(scheduler){

    fun execute(consumer: Consumer<Request,Result>, params: Request?=null){
        buildUseCase(params).single
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe(consumer.success, consumer.error)
    }

    protected abstract fun buildUseCase(params:Request?=null): Stream<Request, Result>
}