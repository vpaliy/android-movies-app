package com.popularmovies.vpaliy.domain.repository

import com.popularmovies.vpaliy.domain.entity.*
import com.popularmovies.vpaliy.domain.interactor.params.Suggestion
import com.popularmovies.vpaliy.domain.interactor.params.TypePage
import com.popularmovies.vpaliy.domain.interactor.params.Stream

interface MediaRepository<T>{
    fun fetchList(request:TypePage): Stream<TypePage,List<T>>
    fun fetchItem(id:String): Stream<String,T>
    fun fetchRoles(item:T): Stream<T,List<Role>>
    fun fetchTrailers(item: T): Stream<T,List<Trailer>>
    fun fetchReviews(item: T): Stream<T,List<Review>>
    fun fetchSuggested(request:Suggestion<T>): Stream<Suggestion<T>,List<T>>
}