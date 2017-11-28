package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.executor.BaseScheduler
import com.popularmovies.vpaliy.domain.repository.PersonalRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersonalInteractor<T> @Inject constructor(private val repository: PersonalRepository<T>, baseScheduler: BaseScheduler)
  :Interactor(baseScheduler)