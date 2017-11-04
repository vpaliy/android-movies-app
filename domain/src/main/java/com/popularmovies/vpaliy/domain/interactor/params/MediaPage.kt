package com.popularmovies.vpaliy.domain.interactor.params

open class MediaPage(var current:Int=1){
    fun next()=apply { ++current }
}