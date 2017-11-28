package com.popularmovies.vpaliy.domain.executor

import io.reactivex.Scheduler

interface BaseScheduler {
  fun io(): Scheduler
  fun ui():Scheduler
}