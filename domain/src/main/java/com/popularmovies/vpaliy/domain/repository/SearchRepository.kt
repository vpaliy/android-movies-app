package com.popularmovies.vpaliy.domain.repository

import com.popularmovies.vpaliy.domain.interactor.params.SearchPage
import com.popularmovies.vpaliy.domain.interactor.params.Stream

interface SearchRepository<T>{
    fun search(page: SearchPage): Stream<SearchPage, List<T>>
}