package com.popularmovies.vpaliy.domain.interactor.params

open class MediaPage(val current:Int=0){
    fun next()=MediaPage(current+1)
}