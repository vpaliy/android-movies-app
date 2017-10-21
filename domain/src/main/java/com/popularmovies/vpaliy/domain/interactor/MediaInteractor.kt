package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.executor.BaseScheduler
import com.popularmovies.vpaliy.domain.interactor.params.Consumer
import com.popularmovies.vpaliy.domain.interactor.params.MediaPage
import com.popularmovies.vpaliy.domain.repository.MediaRepository

abstract class MediaInteractor<T,in Params:MediaPage>
constructor(val repository: MediaRepository<T>, scheduler: BaseScheduler)
    :PagerInteractor<T,Params>(scheduler){

    fun fetchSingle(consumer: Consumer<T>, id:String){
        repository.fetchItem(id)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe(consumer.success,consumer.error)
    }
}