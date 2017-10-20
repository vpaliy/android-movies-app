package com.popularmovies.vpaliy.domain.interactor.utils

open class MediaPage(val current:Int, val limit:Int){
    fun next()=MediaPage(current+1,limit)
}