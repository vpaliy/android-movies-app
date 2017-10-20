package com.popularmovies.vpaliy.domain.repository

import com.popularmovies.vpaliy.domain.entity.PersonalType
import rx.Completable
import rx.Single

interface PersonalRepository<T>{
    fun insert(params:T,type:PersonalType):Completable
    fun delete(params:T,type:PersonalType):Completable
    fun clear(type:PersonalType):Completable
    fun fetch(type:PersonalType):Single<List<T>>
}

//-> personal repository for movies
//-> personal repository for tv shows