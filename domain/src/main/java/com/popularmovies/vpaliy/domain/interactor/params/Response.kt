package com.popularmovies.vpaliy.domain.interactor.params

data class Response<out Request,out Result>(val type:Request, val data:Result)
