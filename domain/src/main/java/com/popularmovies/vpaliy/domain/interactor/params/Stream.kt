package com.popularmovies.vpaliy.domain.interactor.params

import io.reactivex.Single

/* A wrapper for Single<Response<Request,Result>> */
class Stream<Request,Result>(val single:Single<Response<Request, Result>>)
