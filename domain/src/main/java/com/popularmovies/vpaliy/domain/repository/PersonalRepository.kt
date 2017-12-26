package com.popularmovies.vpaliy.domain.repository

import com.popularmovies.vpaliy.domain.entity.PersonalType
import io.reactivex.Completable
import io.reactivex.Single

interface PersonalRepository<T> {
  fun insert(params: T, type: PersonalType): Completable
  fun delete(params: T, type: PersonalType): Completable
  fun clear(type: PersonalType): Completable
  fun fetch(type: PersonalType): Single<List<T>>
}