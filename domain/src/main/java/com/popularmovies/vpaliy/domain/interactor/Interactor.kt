package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.executor.BaseSchedulerProvider

abstract class Interactor(protected val scheduler: BaseSchedulerProvider)
