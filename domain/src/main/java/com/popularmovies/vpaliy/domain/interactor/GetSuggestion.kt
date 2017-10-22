package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.error
import com.popularmovies.vpaliy.domain.executor.BaseSchedulerProvider
import com.popularmovies.vpaliy.domain.ifNotNull
import com.popularmovies.vpaliy.domain.interactor.params.Suggestion
import com.popularmovies.vpaliy.domain.repository.MediaRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetSuggestion<T> @Inject constructor(val repository: MediaRepository<T>, scheduler: BaseSchedulerProvider)
    :RequestInteractor<Suggestion<T>,List<T>>(scheduler){

    override fun buildUseCase(params: Suggestion<T>?)
            =params.ifNotNull(repository::fetchSuggested,error())
}