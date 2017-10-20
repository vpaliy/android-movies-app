package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.executor.BaseScheduler
import com.popularmovies.vpaliy.domain.interactor.utils.Consumer
import com.popularmovies.vpaliy.domain.interactor.utils.MediaPage
import com.popularmovies.vpaliy.domain.repository.Repository

abstract class MediaInteractor<T,in Params:MediaPage>
constructor(val repository: Repository<T>, scheduler: BaseScheduler)
    :PagerInteractor<T,Params>(scheduler){

    fun fetchSingle(consumer: Consumer<T>, id:String){
        repository.fetchItem(id)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe(consumer.success,consumer.error)
    }
}