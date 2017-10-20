package com.popularmovies.vpaliy.domain.repository

import com.popularmovies.vpaliy.domain.entity.*
import rx.Single

interface Repository<T>{
    fun fetchList(type:MovieType):Single<MediaSet<T>>
    fun fetchItem(type: MovieType):Single<T>
    fun fetchRoles(item:T):Single<List<Role>>
    fun fetchTrailers(item: T):Single<List<Trailer>>
    fun fetchReviews(item: T):Single<List<Review>>
    fun fetchBasedOn(item: T,type:SimilarityType):Single<List<T>>
}