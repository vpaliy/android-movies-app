package com.popularmovies.vpaliy.domain.repository

import com.popularmovies.vpaliy.domain.entity.*
import com.popularmovies.vpaliy.domain.interactor.utils.MediaPage
import com.popularmovies.vpaliy.domain.interactor.utils.SimilarPage
import io.reactivex.Single

interface Repository<T>{
    fun fetchList(page:MediaPage): Single<MediaSet<T>> //covered
    fun fetchItem(id:String):Single<T>  //covered
    fun fetchRoles(item:T):Single<List<Role>>
    fun fetchTrailers(item: T):Single<List<Trailer>>
    fun fetchReviews(item: T):Single<List<Review>>
    fun fetchBasedOn(item: T,page:SimilarPage):Single<List<T>>  //covered

    //possible decreasing of cohesion
    fun search(page:MediaPage):Single<List<T>>
}