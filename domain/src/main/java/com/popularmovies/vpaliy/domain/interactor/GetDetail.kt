package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.executor.BaseScheduler
/*
   Get a detail of a media item
 */
abstract class GetDetail<T>(scheduler: BaseScheduler):SingleInteractor<String,T>(scheduler)