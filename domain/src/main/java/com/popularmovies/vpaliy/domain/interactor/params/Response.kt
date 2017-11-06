package com.popularmovies.vpaliy.domain.interactor.params

data class Response<out Request,out Result>(val request:Request, val data:Result)
