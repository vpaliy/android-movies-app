package com.popularmovies.vpaliy.domain.repository

import com.popularmovies.vpaliy.domain.entity.*
import com.popularmovies.vpaliy.domain.interactor.params.Suggestion
import com.popularmovies.vpaliy.domain.interactor.params.TypePage
import com.popularmovies.vpaliy.domain.interactor.params.Stream
import io.reactivex.Single

interface MediaRepository<T>{
    fun fetchList(request:TypePage): Stream<TypePage,List<T>>
    fun fetchSuggested(request:Suggestion<T>): Stream<Suggestion<T>,List<T>>
    fun fetchItem(id:String): Single<T>
    fun fetchRoles(id:String): Single<List<Role>>
    fun fetchTrailers(id:String): Single<List<Trailer>>
    fun fetchReviews(id:String): Single<List<Review>>
}