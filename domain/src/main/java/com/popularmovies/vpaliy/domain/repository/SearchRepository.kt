package com.popularmovies.vpaliy.domain.repository

import com.popularmovies.vpaliy.domain.interactor.params.SearchPage
import io.reactivex.Single

interface SearchRepository<T> {
  fun search(page: SearchPage): Single<List<T>>
}