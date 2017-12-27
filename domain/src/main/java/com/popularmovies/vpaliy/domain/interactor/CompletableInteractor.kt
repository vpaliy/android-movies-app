package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.executor.BaseScheduler
import io.reactivex.Completable

abstract class CompletableInteractor<Params> constructor(scheduler: BaseScheduler) : Interactor(scheduler) {

  fun complete(success: () -> Unit, error: (Throwable) -> Unit, params: Params? = null) {
    buildCompletable(params)
        .subscribeOn(scheduler.io())
        .observeOn(scheduler.ui())
        .subscribe(success, error)
  }

  fun completeWith(success: (Params) -> Unit, error: (Throwable) -> Unit, params: Params) {
    complete({ success.invoke(params) }, error, params)
  }

  abstract fun buildCompletable(params: Params?): Completable
}