package com.popularmovies.vpaliy.domain.interactor.params

open class MediaPage(var current:Int=1){
    val isFirst
        get() = current > 1
    fun next()=apply { ++current }
    fun invalidate()=apply { current=1 }
}