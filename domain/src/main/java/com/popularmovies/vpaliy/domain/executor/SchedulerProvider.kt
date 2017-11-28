package com.popularmovies.vpaliy.domain.executor

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SchedulerProvider @Inject constructor(): BaseScheduler {
  override fun io():Scheduler= Schedulers.io()
  override fun ui(): Scheduler=AndroidSchedulers.mainThread()
}