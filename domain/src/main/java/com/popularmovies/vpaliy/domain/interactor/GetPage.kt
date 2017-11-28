package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.executor.BaseScheduler
import com.popularmovies.vpaliy.domain.interactor.params.TypePage
import com.popularmovies.vpaliy.domain.repository.MediaRepository
import com.popularmovies.vpaliy.domain.wrongArgument
import com.vpaliy.kotlin_extensions.then
import javax.inject.Singleton

@Singleton
class GetPage<T>(val repository: MediaRepository<T>, scheduler: BaseScheduler)
  :SingleInteractor<TypePage,List<T>>(scheduler){

  override fun buildSingle(params: TypePage?)
          =params then(repository::fetchList)?: wrongArgument()
}