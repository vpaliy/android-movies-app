package com.popularmovies.vpaliy.popularmoviesapp.ui.home

import android.support.annotation.StringRes
import com.popularmovies.vpaliy.domain.entity.MovieType
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.Media

object HomeContract{
    interface Presenter{
        fun start()
        fun stop()
        fun more(type:MovieType)
        fun attach(view:View)
    }

    interface View{
        fun show(data:List<Media>,type: MovieType)
        fun message(@StringRes resource:Int)
    }
}