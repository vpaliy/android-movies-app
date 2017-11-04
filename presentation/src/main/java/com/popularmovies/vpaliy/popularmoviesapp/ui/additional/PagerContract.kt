package com.popularmovies.vpaliy.popularmoviesapp.ui.additional

import android.support.annotation.StringRes
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaModel

interface PagerContract {
    interface Presenter{
        fun attachView(view:View)
        fun start()
        fun stop()
        fun more()
    }

    interface View{
        fun showList(data:List<MediaModel>)
        fun append(data:List<MediaModel>)
        fun message(@StringRes resource:Int)
        fun error()
        fun empty()
    }
}