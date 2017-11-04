package com.popularmovies.vpaliy.popularmoviesapp.ui.pager

import com.popularmovies.vpaliy.domain.entity.MediaType
import com.popularmovies.vpaliy.domain.interactor.params.Response
import com.popularmovies.vpaliy.domain.interactor.params.TypePage
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaModel
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.PagerFacade
import com.popularmovies.vpaliy.popularmoviesapp.ui.pager.PagerContract.View
import com.popularmovies.vpaliy.popularmoviesapp.ui.pager.PagerContract.Presenter
import javax.inject.Inject

@ViewScope
class PagerPresenter<T> @Inject
constructor(private val interactor:PagerFacade<T>) :Presenter{

    private lateinit var type:MediaType
    private lateinit var view:View
    private val page by lazy { TypePage(type) }

    override fun attachView(view:View) {
        this.view=view
    }

    override fun attachType(type:MediaType) {
        this.type=type
    }

    override fun more() {
        page.next()
        interactor.execute(this::onSuccess,this::onError,page)
    }

    override fun start()= interactor.execute(this::onSuccess,this::onError,page)

    override fun stop(){}

    private fun onSuccess(page:TypePage,data:List<MediaModel>){
        if(data.isNotEmpty()){
            if(page.current > 1)
                view.append(data)
            else
                view.show(data)
        }else view.empty()
    }

    private fun onError(ex:Throwable){
        ex.printStackTrace()
    }

}