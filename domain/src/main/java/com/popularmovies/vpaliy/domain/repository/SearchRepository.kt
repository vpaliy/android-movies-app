package com.popularmovies.vpaliy.domain.repository

import io.reactivex.Single

interface SearchRepository<T>{
    fun query(query:String): Single<List<T>>
    fun next():Single<List<T>>
}