package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.executor.BaseScheduler
import io.reactivex.Single

abstract class SingleInteractor<Request, Response> (
    scheduler: BaseScheduler
) : Interactor(scheduler) {
  fun execute(success: (Response) -> Unit, error: (Throwable) -> Unit, params: Request? = null) {
    buildSingle(params)
        .subscribeOn(scheduler.io())
        .observeOn(scheduler.ui())
        .subscribe(success, error)
  }

  fun execute(success: (Request, Response) -> Unit, error: (Throwable) -> Unit, params: Request? = null) {
    execute({ response -> success.invoke(params!!, response) }, error, params)
  }

  protected abstract fun buildSingle(params: Request? = null): Single<Response>
}