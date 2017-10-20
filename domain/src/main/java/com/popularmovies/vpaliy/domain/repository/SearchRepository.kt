package com.popularmovies.vpaliy.domain.repository
import rx.Single

interface SearchRepository<T>{
    fun query(query:String):Single<List<T>>
    fun next():Single<List<T>>
}