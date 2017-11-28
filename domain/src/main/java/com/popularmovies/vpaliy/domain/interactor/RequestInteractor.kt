package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.executor.BaseScheduler
import com.popularmovies.vpaliy.domain.interactor.params.Consumer

abstract class RequestInteractor<Request,Result>
constructor(scheduler: BaseScheduler):Interactor(scheduler){

    fun execute(consumer: Consumer<Request,Result>, params: Request?=null){
    }
}