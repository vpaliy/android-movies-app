package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.entity.MediaSet
import com.popularmovies.vpaliy.domain.executor.BaseScheduler
import com.popularmovies.vpaliy.domain.interactor.params.Consumer
import com.popularmovies.vpaliy.domain.interactor.params.MediaPage

abstract class PagerInteractor<out T,in Request:MediaPage>
constructor(scheduler: BaseScheduler):Interactor(scheduler){
    abstract fun fetchPage(consumer:Consumer<MediaSet<T>>, params:Request)
}