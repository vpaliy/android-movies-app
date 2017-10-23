package com.popularmovies.vpaliy.data

fun <T,R> MutableList<R>.copy(array: Array<T>, copy:(T)->R):List<R>{
    array.forEach { add(copy(it)) }
    return this
}