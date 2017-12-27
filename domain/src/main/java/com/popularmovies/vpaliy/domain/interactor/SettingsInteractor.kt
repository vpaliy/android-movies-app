package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.executor.BaseScheduler
import io.reactivex.Completable

class SettingsInteractor(scheduler: BaseScheduler):CompletableInteractor<Any>(scheduler){
  override fun buildCompletable(params: Any?): Completable {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}