package com.popularmovies.vpaliy.popularmoviesapp.ui.model

import com.popularmovies.vpaliy.data.mapper.Mapper
import com.popularmovies.vpaliy.domain.interactor.RequestInteractor
import com.popularmovies.vpaliy.domain.interactor.params.Consumer
import com.popularmovies.vpaliy.domain.interactor.params.Response
import com.popularmovies.vpaliy.domain.interactor.params.TypePage

class PagerFacade<T>(private val interactor:RequestInteractor<TypePage,List<T>>,
                     private val mapper: Mapper<MediaModel,T>){
    private var success:((TypePage,List<MediaModel>)->Unit)?=null

    fun execute(success:(TypePage, List<MediaModel>)->Unit, error:(Throwable)->Unit, page: TypePage?){
        this.success=success
        interactor.execute(Consumer(this::onSuccess,error),page)
    }

    private fun onSuccess(response: Response<TypePage,List<T>>) {
        success?.invoke(response.request, mapper.map(response.data))
    }
}