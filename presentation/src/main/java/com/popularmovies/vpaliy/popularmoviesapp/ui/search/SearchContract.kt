package com.popularmovies.vpaliy.popularmoviesapp.ui.search

import com.popularmovies.vpaliy.domain.entity.Actor
import com.popularmovies.vpaliy.domain.entity.Movie
import com.popularmovies.vpaliy.domain.entity.TVShow
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.SearchType

object SearchContract{
    interface View{
        fun showMovies(data:List<Movie>)
        fun showTV(data:List<TVShow>)
        fun showPeople(data:List<Actor>)
        fun appendMovies(data:List<Movie>)
        fun appendTV(data:List<TVShow>)
        fun appendPeople(data:List<Actor>)
        fun empty(type: SearchType)
        fun error(type: SearchType)
    }

    interface Presenter{
        fun query(query:String)
        fun more(type: SearchType)
        fun stop()
        fun attachView(view:View)
    }
}