package com.popularmovies.vpaliy.popularmoviesapp.ui.more

import android.support.annotation.StringRes
import com.popularmovies.vpaliy.domain.entity.MediaType
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseView
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaModel

object MoreContract {
  interface Presenter {
    fun attachView(view: View)
    fun start()
    fun more()
  }

  interface View : BaseView {
    fun show(data: List<MediaModel>)
    fun append(data: List<MediaModel>)
    fun showLoading()
    fun hideLoading()
    fun error(@StringRes resource: Int)
    fun message(@StringRes resource: Int)
    fun showTitle(@StringRes resource: Int)
    fun empty()
  }
}