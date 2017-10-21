package com.popularmovies.vpaliy.domain.repository

import com.popularmovies.vpaliy.domain.interactor.params.MediaPage
import io.reactivex.Single

interface SearchRepository<T>{
    fun search(page: MediaPage):Single<List<T>>
}