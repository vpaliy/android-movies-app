package com.popularmovies.vpaliy.domain.interactor.params

class Consumer<in Request,in Result>(val success:(Response<Request, Result>)->Unit,val error:(Throwable)->Unit)