package com.popularmovies.vpaliy.popularmoviesapp.ui.model

import com.popularmovies.vpaliy.domain.entity.Review
import com.popularmovies.vpaliy.domain.entity.Role
import com.popularmovies.vpaliy.domain.entity.Trailer
import com.popularmovies.vpaliy.domain.interactor.RequestInteractor
import com.popularmovies.vpaliy.domain.interactor.params.Suggestion

class MediaFacade<T>(private val id:String,
                     private val getItem:RequestInteractor<String,T>,
                     private val getReviews:RequestInteractor<T,List<Review>>,
                     private val getRoles:RequestInteractor<T,List<Role>>,
                     private val getTrailers:RequestInteractor<T,List<Trailer>>,
                     private val getSuggestions:RequestInteractor<Suggestion<T>,List<T>>){

    var item:T?=null




}