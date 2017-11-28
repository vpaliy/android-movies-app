package com.popularmovies.vpaliy.domain

import io.reactivex.Single

fun<T> wrongArgument():Single<T> = Single.error<T>(IllegalArgumentException())