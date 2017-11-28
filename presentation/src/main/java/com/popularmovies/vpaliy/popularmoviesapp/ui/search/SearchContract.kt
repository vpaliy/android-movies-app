package com.popularmovies.vpaliy.popularmoviesapp.ui.search

object SearchContract{
  interface View<in T>{
    fun showResult(data:List<T>)
    fun appendResult(data:List<T>)
    fun showLoading()
    fun hideLoading()
    fun empty()
    fun error()
  }

  interface Presenter<out T>{
    fun query(query:String)
    fun more()
    fun stop()
    fun attachView(view:View<T>)
  }
}