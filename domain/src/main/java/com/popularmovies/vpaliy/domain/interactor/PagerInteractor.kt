package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.entity.MediaSet
import com.popularmovies.vpaliy.domain.executor.BaseScheduler
import com.popularmovies.vpaliy.domain.interactor.utils.Consumer
import com.popularmovies.vpaliy.domain.interactor.utils.MediaPage

abstract class PagerInteractor<out T,in Page:MediaPage>
constructor(scheduler: BaseScheduler):Interactor(scheduler){
    abstract fun fetchPage(consumer:Consumer<MediaSet<T>>, params:Page)
}