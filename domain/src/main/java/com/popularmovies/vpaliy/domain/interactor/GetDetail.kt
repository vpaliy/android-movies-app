package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.executor.BaseScheduler

abstract class GetDetail<T>(scheduler: BaseScheduler):SingleInteractor<String,T>(scheduler)