package com.popularmovies.vpaliy.domain

import com.popularmovies.vpaliy.domain.interactor.params.Result
import io.reactivex.Single

fun<T> wrongArgument()= Single.error<T>(IllegalArgumentException())!!

fun <Request, Response> Single<Response>.toResult(request:Request)= Result(request,this)
