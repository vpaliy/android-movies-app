package com.popularmovies.vpaliy.popularmoviesapp.ui.home

import android.support.annotation.StringRes
import com.popularmovies.vpaliy.domain.entity.MediaType
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaModel

object HomeContract{
    interface Presenter{
        fun stop()
        fun start(types:Array<MediaType>)
        fun more(type: MediaType)
        fun attach(view:View)
    }

    interface View{
        fun show(data:List<MediaModel>, type: MediaType)
        fun append(data:List<MediaModel>, type:MediaType)
        fun error()
        fun empty()
        fun message(@StringRes resource:Int)
    }
}