package com.popularmovies.vpaliy.popularmoviesapp.ui.additional

import com.popularmovies.vpaliy.domain.entity.MediaType
import com.popularmovies.vpaliy.domain.interactor.RequestInteractor
import com.popularmovies.vpaliy.domain.interactor.params.Consumer
import com.popularmovies.vpaliy.domain.interactor.params.Response
import com.popularmovies.vpaliy.domain.interactor.params.TypePage
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaModel
import com.popularmovies.vpaliy.popularmoviesapp.ui.additional.PagerContract.View
import com.popularmovies.vpaliy.popularmoviesapp.ui.additional.PagerContract.Presenter
import javax.inject.Inject

@ViewScope
class PagerPresenter @Inject
constructor(val interactor:RequestInteractor<TypePage,List<MediaModel>>) :Presenter{

    private lateinit var view:View
    private lateinit var type:MediaType
    private val page by lazy { TypePage(type) }

    override fun attachView(view:View) {
        this.view=view
    }

    override fun more() {
        page.next()
        interactor.execute(Consumer(this::onAppend,this::error),page)
    }

    override fun start() {
        interactor.execute(Consumer(this::onSuccess,this::error),page)
    }

    override fun stop() {
    }

    private fun error(ex:Throwable){}

    private fun onAppend(response: Response<TypePage,List<MediaModel>>){}

    private fun onSuccess(response: Response<TypePage,List<MediaModel>>){}
}