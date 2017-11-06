package com.popularmovies.vpaliy.popularmoviesapp.ui.search

import com.popularmovies.vpaliy.domain.entity.Actor
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaModel
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.SearchType

object SearchContract{
    interface View{
        fun showPeople(data:List<Actor>)
        fun showMedia(type:SearchType,data:List<MediaModel>)
        fun appendMedia(type:SearchType,data:List<MediaModel>)
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