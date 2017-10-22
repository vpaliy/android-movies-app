package com.popularmovies.vpaliy.domain.repository

import com.popularmovies.vpaliy.domain.entity.PersonalType
import com.popularmovies.vpaliy.domain.interactor.params.Stream
import io.reactivex.Completable

interface PersonalRepository<T>{
    fun insert(params:T,type:PersonalType): Completable
    fun delete(params:T,type:PersonalType):Completable
    fun clear(type:PersonalType):Completable
    fun fetch(type:PersonalType): Stream<PersonalType, T>
}

//-> personal repository for movies
//-> personal repository for tv shows