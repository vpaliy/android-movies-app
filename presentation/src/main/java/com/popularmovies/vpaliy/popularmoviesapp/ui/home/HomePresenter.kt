package com.popularmovies.vpaliy.popularmoviesapp.ui.home

import com.popularmovies.vpaliy.domain.entity.MediaType
import com.popularmovies.vpaliy.domain.interactor.params.TypePage
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaModel
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.PagerFacade
import com.popularmovies.vpaliy.popularmoviesapp.ui.home.HomeContract.View

class HomePresenter<T>(private val interactor:PagerFacade<T>) :HomeContract.Presenter{

    private val map= mutableMapOf<MediaType,TypePage>()
    private lateinit var view:View

    override fun attach(view:View) {
        this.view=view
    }

    override fun more(type: MediaType) {
        map[type]?.let {
            it.next()
            interactor.execute(this::onSuccess,this::onError,it)
        }
    }

    private fun onError(ex:Throwable){
        ex.printStackTrace()
        view.error()
    }

    private fun onSuccess(page: TypePage,data:List<MediaModel>){
        if(data.isNotEmpty()){
            if(page.current > 1)
                view.append(data,page.type)
            else
                view.show(data,page.type)
        }else view.empty()
    }

    override fun start(types: Array<MediaType>) {
        types.forEach {
            map[it]= TypePage(it)
            interactor.execute(this::onSuccess,this::onError,map[it])
        }
    }

    override fun stop()=map.clear()
}