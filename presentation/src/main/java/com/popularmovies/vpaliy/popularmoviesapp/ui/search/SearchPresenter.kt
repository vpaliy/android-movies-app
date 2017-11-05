package com.popularmovies.vpaliy.popularmoviesapp.ui.search

import com.popularmovies.vpaliy.domain.entity.Actor
import com.popularmovies.vpaliy.domain.entity.Movie
import com.popularmovies.vpaliy.domain.entity.TVShow
import com.popularmovies.vpaliy.popularmoviesapp.ui.search.SearchContract.View
import com.popularmovies.vpaliy.domain.interactor.params.SearchPage
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.SearchType
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.SearchFacade
import com.popularmovies.vpaliy.popularmoviesapp.ui.then

@ViewScope
class SearchPresenter(val searchFacade: SearchFacade):SearchContract.Presenter{

    private lateinit var page:SearchPage
    private lateinit var view:View

    init {
        searchFacade.onError=this::onError
        searchFacade.onPeople=this::onPeopleSuccess
        searchFacade.onTV=this::onTVSuccess
        searchFacade.onMovie=this::onMovieSuccess
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

    private fun onMovieSuccess(page:SearchPage,data:List<Movie>){
        if(data.isNotEmpty()){
            page.isFirst.then({view.showMovies(data)})
                    ?:view.appendMovies(data)
        }else {
            view.empty(SearchType.MOVIE)
        }
    }

    private fun onPeopleSuccess(page:SearchPage,data:List<Actor>){
        if(data.isNotEmpty()){
            page.isFirst.then({view.showPeople(data)})
                    ?:view.appendPeople(data)
        }else {
            view.empty(SearchType.PEOPLE)
        }
    }

    private fun onTVSuccess(page:SearchPage,data:List<TVShow>){
        if(data.isNotEmpty()){
            page.isFirst.then({view.showTV(data)})
                    ?:view.appendTV(data)
        }else {
            view.empty(SearchType.TV)
        }
    }

    override fun attachView(view: View) {
        this.view=view
    }

    private fun onError(ex:Throwable){
        ex.printStackTrace()
    }
}