package com.popularmovies.vpaliy.domain

import com.popularmovies.vpaliy.domain.interactor.params.Response
import com.popularmovies.vpaliy.domain.interactor.params.Stream
import io.reactivex.Single

fun <T,Type> Type?.ifNotNull(source:(Type)->T, default:T) =if(this!=null) source(this) else default

fun <Request,Result> Single<Response<Request, Result>>.toStream()= Stream(this)

fun<Request,Result> error() =Single.error<Response<Request, Result>>(IllegalArgumentException()).toStream()

fun <Request,Result> Single<Result>.toStream(request:Request)=Stream(map{Response(request,it)})