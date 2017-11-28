package com.popularmovies.vpaliy.domain.repository

import com.popularmovies.vpaliy.domain.entity.*
import com.popularmovies.vpaliy.domain.interactor.params.Suggestion
import com.popularmovies.vpaliy.domain.interactor.params.TypePage
import io.reactivex.Single

interface MediaRepository<T>{
    fun fetchList(request:TypePage): Single<List<T>>
    fun fetchSuggested(request:Suggestion): Single<List<T>>
    fun fetchItem(id:String): Single<T>
    fun fetchRoles(id:String): Single<List<Role>>
    fun fetchTrailers(id:String): Single<List<Trailer>>
    fun fetchReviews(id:String): Single<List<Review>>
}