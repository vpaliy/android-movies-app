package com.popularmovies.vpaliy.data

import com.popularmovies.vpaliy.domain.interactor.params.MediaPage
import com.vpaliy.tmdb.query.QueryBuilder

fun <T,R> MutableList<R>.copy(array: Array<T>, copy:(T)->R):List<R>{
    array.forEach { add(copy(it)) }
    return this
}

fun MediaPage.buildQuery()=QueryBuilder().limit(limit).page(current).build()