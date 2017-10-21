package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.entity.MediaSet
import com.popularmovies.vpaliy.domain.executor.BaseSchedulerProvider
import com.popularmovies.vpaliy.domain.interactor.params.Consumer
import com.popularmovies.vpaliy.domain.interactor.params.SuggestionRequest
import com.popularmovies.vpaliy.domain.repository.MediaRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetSuggestion<T> @Inject
constructor(val repository: MediaRepository<T>, scheduler: BaseSchedulerProvider)
    :PagerInteractor<T, SuggestionRequest<T>>(scheduler){

    override fun fetchPage(consumer: Consumer<MediaSet<T>>, params: SuggestionRequest<T>) {
        repository.fetchSuggested(params)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe()
    }
}