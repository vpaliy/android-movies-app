package com.popularmovies.vpaliy.popularmoviesapp.ui.search

import android.support.annotation.StringRes

object SearchContract {
  interface View<in T> {
    fun showResult(data: List<T>)
    fun appendResult(data: List<T>)
    fun showLoading()
    fun hideLoading()
    fun empty()
    fun error(@StringRes resource: Int)
    fun message(@StringRes resource: Int)
  }

  interface Presenter<out T> {
    fun query(query: String)
    fun more()
    fun attachView(view: View<T>)
  }
}