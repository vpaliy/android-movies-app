package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.interactor.utils.Consumer


interface MediaInteractor<out T,in Params>{

    fun fetchStream(consumer: Consumer<List<T>>){
    }

    fun fetchSingle(consumer: Consumer<T>, params:Params?=null){

    }
}