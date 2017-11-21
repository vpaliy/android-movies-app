package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.executor.BaseScheduler
import com.popularmovies.vpaliy.domain.repository.MediaRepository
import com.popularmovies.vpaliy.domain.wrongArgument
import com.vpaliy.kotlin_extensions.then
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetItem<T> @Inject constructor(val repository: MediaRepository<T>, scheduler: BaseScheduler)
    :GetDetail<T>(scheduler){

    override fun buildUseCase(params: String?): Single<T> {
        return params then(repository::fetchItem)
                ?: wrongArgument()
    }
}