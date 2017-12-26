package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.entity.Actor
import com.popularmovies.vpaliy.domain.executor.BaseScheduler
import com.popularmovies.vpaliy.domain.repository.Repository
import com.popularmovies.vpaliy.domain.wrongArgument
import com.vpaliy.kotlin_extensions.then
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetActor @Inject constructor(var repository: Repository, scheduler: BaseScheduler)
  : SingleInteractor<String, Actor>(scheduler) {

  override fun buildSingle(params: String?) =
      params then (repository::fetchActor) ?: wrongArgument()
}