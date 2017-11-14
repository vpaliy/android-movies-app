package com.popularmovies.vpaliy.data

import android.util.Log
import com.popularmovies.vpaliy.domain.interactor.params.MediaPage
import com.vpaliy.tmdb.query.QueryBuilder

inline fun <T,R> MutableList<R>.copy(array: Array<T>, copy:(T)->R):List<R>{
    array.forEach { add(copy(it)) }
    return this
}

fun Any.log(message:Any?){
    Log.d(this.javaClass.name,message.toString())
}

fun MediaPage.buildQuery()=QueryBuilder().page(current).build()