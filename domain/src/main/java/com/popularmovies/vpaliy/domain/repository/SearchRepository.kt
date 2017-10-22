package com.popularmovies.vpaliy.domain.repository

import com.popularmovies.vpaliy.domain.interactor.params.MediaPage
import com.popularmovies.vpaliy.domain.interactor.params.Stream

interface SearchRepository<T>{
    fun search(page: MediaPage): Stream<MediaPage, List<T>>
}