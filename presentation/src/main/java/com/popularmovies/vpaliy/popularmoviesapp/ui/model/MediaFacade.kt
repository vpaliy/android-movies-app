package com.popularmovies.vpaliy.popularmoviesapp.ui.model

import com.popularmovies.vpaliy.domain.entity.Review
import com.popularmovies.vpaliy.domain.entity.Role
import com.popularmovies.vpaliy.domain.entity.SimilarityType
import com.popularmovies.vpaliy.domain.entity.Trailer
import com.popularmovies.vpaliy.domain.interactor.GetDetail
import com.popularmovies.vpaliy.domain.interactor.GetSuggestion
import com.popularmovies.vpaliy.domain.interactor.params.Suggestion

class MediaFacade<T>(private val getItem: GetDetail<T>,
                     private val getReviews: GetDetail<List<Review>>,
                     private val getTrailers: GetDetail<List<Trailer>>,
                     private val getRoles: GetDetail<List<Role>>,
                     private val getSuggestion: GetSuggestion<T>){

  lateinit var id:String
  
  private val suggestionMap by lazy (LazyThreadSafetyMode.NONE){
    mutableMapOf<SimilarityType, Suggestion>()
  }

  fun getItem(success:(T)->Unit, error:(Throwable)->Unit){
    getItem.execute(success,error,id)
  }

  fun getReviews(success: (List<Review>) -> Unit, error: (Throwable) -> Unit){
    getReviews.execute(success,error,id)
  }

  fun getTrailers(success: (List<Trailer>) -> Unit, error: (Throwable) -> Unit){
    getTrailers.execute(success,error,id)
  }

  fun getRoles(success: (List<Role>) -> Unit, error: (Throwable) -> Unit){
    getRoles.execute(success,error,id)
  }

  fun getSuggestion(success:(Suggestion?, List<T>)->Unit, error: (Throwable) -> Unit, type:SimilarityType){
    val suggestion = suggestionMap[type]?: Suggestion(id,type)
    suggestion.invalidate()
    suggestionMap.put(type,suggestion)
    getSuggestion.execute(success,error,suggestion)
  }

  fun moreSuggestions(success:(Suggestion?, List<T>)->Unit, error: (Throwable) -> Unit, type:SimilarityType){
    val suggestion=suggestionMap[type]
    suggestion?.let {
      it.next()
      getSuggestion.execute(success,error,it)
    }
  }
}