package com.popularmovies.vpaliy.domain.executor

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TestScheduler @Inject constructor(): BaseScheduler {
    override fun io(): Scheduler = Schedulers.trampoline()
    override fun ui(): Scheduler =Schedulers.trampoline()
}