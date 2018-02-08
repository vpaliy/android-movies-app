package com.popularmovies.vpaliy.popularmoviesapp.ui.search

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.popularmovies.vpaliy.popularmoviesapp.R
import com.popularmovies.vpaliy.popularmoviesapp.ui.showErrorMessage
import com.popularmovies.vpaliy.popularmoviesapp.ui.showMessage
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.OnReachBottomListener
import com.vpaliy.kotlin_extensions.turnOff
import kotlinx.android.synthetic.main.fragment_search.*

abstract class SearchResult<T> : Fragment(), SearchContract.View<T>, QueryListener {
  abstract var presenter: SearchContract.Presenter<T>?

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    inject()
    refresher.turnOff()
    result.addOnScrollListener(object : OnReachBottomListener(result.layoutManager) {
      override fun onLoadMore() {
        presenter?.more()
      }
    })
  }

  override fun queryTyped(query: String) {
    presenter?.query(query)
  }

  override fun error(resource: Int) {
    showErrorMessage(resource)
  }

  override fun message(resource: Int) {
    showMessage(resource)
  }
  override fun showLoading() {
    refresher.isRefreshing = false
  }

  override fun hideLoading() {
    refresher.isRefreshing = false
  }

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?)
      : View?
      = inflater?.inflate(R.layout.fragment_search, container, false)

  abstract fun inject()
}