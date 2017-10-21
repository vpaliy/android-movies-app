package com.popularmovies.vpaliy.domain.executor

import io.reactivex.Scheduler

interface BaseSchedulerProvider {
    fun io(): Scheduler
    fun ui():Scheduler
}