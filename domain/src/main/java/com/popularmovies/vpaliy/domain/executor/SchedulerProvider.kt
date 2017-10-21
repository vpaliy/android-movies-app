package com.popularmovies.vpaliy.domain.executor

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SchedulerProvider:BaseSchedulerProvider{
    override fun io():Scheduler= Schedulers.io()
    override fun ui(): Scheduler=AndroidSchedulers.mainThread()
}