package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.executor.BaseScheduler
import io.reactivex.Single

abstract class SingleInteractor<in Request,Result>
constructor(scheduler: BaseScheduler):Interactor(scheduler) {

    fun execute(success:(Result)->Unit,error:(Throwable)->Unit, params: Request?=null){
        buildUseCase(params)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe(success, error)
    }

    protected abstract fun buildUseCase(params:Request?=null): Single<Result>
}