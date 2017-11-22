package com.popularmovies.vpaliy.popularmoviesapp.ui.detail

import com.popularmovies.vpaliy.data.mapper.Mapper
import com.popularmovies.vpaliy.domain.entity.Movie
import com.popularmovies.vpaliy.domain.entity.SimilarityType
import com.popularmovies.vpaliy.domain.interactor.params.Response
import com.popularmovies.vpaliy.domain.interactor.params.Suggestion
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope
import com.popularmovies.vpaliy.popularmoviesapp.ui.detail.DetailContract.View
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaFacade
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaModel

@ViewScope
class DetailPresenter(private val facade: MediaFacade<Movie>,
                      private val mapper:Mapper<MediaModel,Movie>):DetailContract.Presenter{
    lateinit var view:View

    override fun attachView(view: View) {
        this.view=view
    }

    override fun start() {
        facade.getItem(view::showMedia,this::handleError)
      //  facade.getReviews(view::showReviews,this::handleError)
        facade.getRoles(view::showCast,this::handleError)
        facade.getTrailers(view::showTrailers,this::handleError)
        SimilarityType.values().forEach {
            facade.getSuggestion(this::catchSuggestion,this::handleError,it)
        }
    }

    override fun stop() {}

    override fun more(type: SimilarityType) {
        facade.moreSuggestions(this::appendSuggestion,this::handleError,type)
    }

    private fun appendSuggestion(response: Response<Suggestion<Movie>,List<Movie>>){
        val type=response.request.type
        val data=mapper.map(response.data)
        view.appendSuggested(type,data)
    }

    private fun catchSuggestion(response: Response<Suggestion<Movie>, List<Movie>>){
        val type=response.request.type
        val data=mapper.map(response.data)
        view.showSuggested(type,data)
    }

    private fun handleError(ex:Throwable){
        ex.printStackTrace()
    }

    override fun attachId(id: String) {
        facade.id=id
    }
}