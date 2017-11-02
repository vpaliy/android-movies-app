package com.popularmovies.vpaliy.popularmoviesapp.ui.home

import com.popularmovies.vpaliy.data.mapper.Mapper
import com.popularmovies.vpaliy.domain.entity.MovieType
import com.popularmovies.vpaliy.domain.interactor.RequestInteractor
import com.popularmovies.vpaliy.domain.interactor.params.Consumer
import com.popularmovies.vpaliy.domain.interactor.params.Response
import com.popularmovies.vpaliy.domain.interactor.params.TypePage
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.Media

abstract class HomePresenter<T>(val request:RequestInteractor<TypePage,List<T>>,
                                val mapper: Mapper<Media,T>):HomeContract.Presenter {

    private val map= mutableMapOf<MovieType,TypePage>()
    private lateinit var view:HomeContract.View

    override fun more(type: MovieType) {
        map[type]?.let {
            request.execute(Consumer(this::onSuccess,this::onError),it)
        }
    }

    override fun start(types: Array<MovieType>) {
        types.forEach {
            map[it]= TypePage(it)
            request.execute(Consumer(this::onSuccess,this::onError),map[it])
        }
    }

    private fun onSuccess(response:Response<TypePage,List<T>>){
        if(response.data.isNotEmpty()){
            view.show(mapper.map(response.data),response.type.type)
        }else view.empty()
    }

    private fun onError(ex:Throwable){
        ex.printStackTrace()
        view.error()
    }

    override fun stop()= map.clear()

    override fun attach(view: HomeContract.View) {
        this.view=view
    }
}