package com.popularmovies.vpaliy.popularmoviesapp.ui.model

import com.popularmovies.vpaliy.domain.entity.Review
import com.popularmovies.vpaliy.domain.entity.Role
import com.popularmovies.vpaliy.domain.entity.Trailer
import com.popularmovies.vpaliy.domain.interactor.GetDetail

class MediaFacade<T>(val id:String,
                     val getItem: GetDetail<T>,
                     val getReviews: GetDetail<List<Review>>,
                     val getTrailers: GetDetail<List<Trailer>>,
                     val getRoles: GetDetail<List<Role>>){


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