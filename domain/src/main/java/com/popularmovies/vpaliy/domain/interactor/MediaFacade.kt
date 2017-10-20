package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.entity.MediaSet
import com.popularmovies.vpaliy.domain.entity.Role
import com.popularmovies.vpaliy.domain.entity.Trailer
import com.popularmovies.vpaliy.domain.interactor.utils.Consumer
import com.popularmovies.vpaliy.domain.model.Review
import com.popularmovies.vpaliy.domain.interactor.utils.MediaPage
import javax.inject.Inject

class MediaFacade<T,in Params:MediaPage> @Inject
constructor(val reviews:SingleInteractor<List<Review>,T>,
            val trailers:SingleInteractor<List<Trailer>,T>,
            val roles:SingleInteractor<List<Role>,T>,
            val suggestion:PagerInteractor<T,Params>,
            val mediaInteractor: MediaInteractor<T,Params>){

    fun fetchTrailers(consumer:Consumer<List<Trailer>>,item:T)
            =trailers.execute(consumer,item)

    fun fetchReviews(consumer: Consumer<List<Review>>, item:T)
            =reviews.execute(consumer,item)

    fun fetchRoles(consumer: Consumer<List<Role>>,item:T)
            =roles.execute(consumer,item)

    fun fetchItem(consumer: Consumer<T>, id:String)
            =mediaInteractor.fetchSingle(consumer,id)

    fun fetchPage(consumer: Consumer<MediaSet<T>>, params:Params)
            =mediaInteractor.fetchPage(consumer,params)

    fun fetchSuggestion(consumer: Consumer<MediaSet<T>>, params:Params)
            =suggestion.fetchPage(consumer,params)
}