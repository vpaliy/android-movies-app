package com.popularmovies.vpaliy.domain

import com.popularmovies.vpaliy.domain.interactor.params.Response
import com.popularmovies.vpaliy.domain.interactor.params.Stream
import io.reactivex.Single

fun <Request,Result> Single<Response<Request, Result>>.toStream()= Stream(this)

fun<Request,Result> error() =Single.error<Response<Request, Result>>(IllegalArgumentException()).toStream()

fun<T> wrongArgument()= Single.error<T>(IllegalArgumentException())

fun <Request,Result> Single<Result>.toStream(request:Request)=Stream(map{Response(request,it)})
