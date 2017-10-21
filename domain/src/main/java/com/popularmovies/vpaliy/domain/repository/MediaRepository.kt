package com.popularmovies.vpaliy.domain.repository

import com.popularmovies.vpaliy.domain.entity.*
import com.popularmovies.vpaliy.domain.interactor.params.MediaPage
import com.popularmovies.vpaliy.domain.interactor.params.SuggestionRequest
import io.reactivex.Single

interface MediaRepository<T>{
    fun fetchList(page:MediaPage): Single<MediaSet<T>>
    fun fetchItem(id:String):Single<T>
    fun fetchRoles(item:T):Single<List<Role>>
    fun fetchTrailers(item: T):Single<List<Trailer>>
    fun fetchReviews(item: T):Single<List<Review>>
    fun fetchSuggested(page: SuggestionRequest<T>):Single<List<T>>
}