package com.popularmovies.vpaliy.popularmoviesapp.ui.model

import com.popularmovies.vpaliy.domain.entity.Review
import com.popularmovies.vpaliy.domain.entity.Role
import com.popularmovies.vpaliy.domain.entity.Trailer
import com.popularmovies.vpaliy.domain.interactor.GetDetail

class MediaFacade<out T>(private val getItem: GetDetail<T>,
                         private val getReviews: GetDetail<List<Review>>,
                         private val getTrailers: GetDetail<List<Trailer>>,
                         private val getRoles: GetDetail<List<Role>>){

    lateinit var id:String

    fun fetchItem(success:(T)->Unit,error:(Throwable)->Unit){
        getItem.execute(success,error,id)
    }

    fun fetchReviews(success: (List<Review>) -> Unit,error: (Throwable) -> Unit){
        getReviews.execute(success,error,id)
    }

    fun fetchTrailers(success: (List<Trailer>) -> Unit, error: (Throwable) -> Unit){
        getTrailers.execute(success,error,id)
    }

    fun fetchRoles(success: (List<Role>) -> Unit, error: (Throwable) -> Unit){
        getRoles.execute(success,error,id)
    }
}