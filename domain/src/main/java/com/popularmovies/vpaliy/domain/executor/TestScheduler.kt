package com.popularmovies.vpaliy.domain.executor

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class TestScheduler: BaseScheduler {
    override fun io(): Scheduler = Schedulers.trampoline()
    override fun ui(): Scheduler =Schedulers.trampoline()
}