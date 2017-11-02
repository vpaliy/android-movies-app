package com.popularmovies.vpaliy.popularmoviesapp.ui.home

import android.support.annotation.StringRes
import com.popularmovies.vpaliy.domain.entity.MovieType
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.Media

object HomeContract{
    interface Presenter{
        fun stop()
        fun start(types:Array<MovieType>)
        fun more(type:MovieType)
        fun attach(view:View)
    }

    interface View{
        fun show(data:List<Media>,type: MovieType)
        fun error()
        fun empty()
        fun message(@StringRes resource:Int)
    }
}