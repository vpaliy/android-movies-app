package com.popularmovies.vpaliy.domain.executor

import rx.Scheduler

interface BaseScheduler{
    fun io():Scheduler
    fun ui():Scheduler
}