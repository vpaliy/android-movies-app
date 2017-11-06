package com.popularmovies.vpaliy.popularmoviesapp.ui.search

import com.popularmovies.vpaliy.domain.entity.Actor
import com.popularmovies.vpaliy.popularmoviesapp.ui.search.SearchContract.View
import com.popularmovies.vpaliy.domain.interactor.params.SearchPage
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaModel
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.SearchType
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.SearchFacade
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.QueryResult

@ViewScope
class SearchPresenter(val searchFacade: SearchFacade):SearchContract.Presenter{

    private lateinit var page:SearchPage
    private lateinit var view:View

    init {
        searchFacade.onError=this::onError
        searchFacade.onPeople=this::onPeople
        searchFacade.onSuccess=this::onMedia
    }

    override fun query(query: String) {
        page= SearchPage(query)
        searchFacade.search(page)
    }

    override fun more(type: SearchType) {
        page.next()
        searchFacade.search(page,type)
    }

    override fun stop() {
        page.invalidate()
    }

    private fun onPeople(result: QueryResult<Actor>){
        val data=result.data
        if(data.isNotEmpty()){
            if(result.isFirst)
                view.showPeople(result.data)
            else
                view.showPeople(result.data)
        }
    }

    private fun onMedia(result: QueryResult<MediaModel>){
        val data=result.data
        if(data.isNotEmpty()){
            if(result.isFirst)
                view.showMedia(result.type,result.data)
            else
                view.appendMedia(result.type,result.data)
        }
    }

    override fun attachView(view: View) {
        this.view=view
    }

    private fun onError(type:SearchType, ex:Throwable){
        ex.printStackTrace()
        view.error(type)
    }
}