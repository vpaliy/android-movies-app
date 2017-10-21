package com.popularmovies.vpaliy.domain.interactor.params

open class MediaPage(val current:Int, val limit:Int){
    fun next()=MediaPage(current+1,limit)
}