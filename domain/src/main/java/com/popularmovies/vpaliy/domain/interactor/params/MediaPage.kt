package com.popularmovies.vpaliy.domain.interactor.params

open class MediaPage(val current:Int=1){
    fun next()=MediaPage(current+1)
}