package com.popularmovies.vpaliy.popularmoviesapp.ui.search

import android.os.Bundle
import android.support.annotation.TransitionRes
import android.support.v4.app.Fragment
import android.transition.Transition
import android.transition.TransitionInflater
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.popularmovies.vpaliy.popularmoviesapp.R
import com.popularmovies.vpaliy.popularmoviesapp.ui.showErrorMessage
import com.popularmovies.vpaliy.popularmoviesapp.ui.showMessage
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.OnReachBottomListener
import com.popularmovies.vpaliy.popularmoviesapp.ui.view.TransitionListenerAdapter
import com.vpaliy.kotlin_extensions.hide
import com.vpaliy.kotlin_extensions.show
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

  override fun inputCleared() {
    if (refresher.visibility != View.GONE) {
      val transition = getTransition(R.transition.search_hide_result)
      TransitionManager.beginDelayedTransition(refresher, transition)
      transition.addListener(object : TransitionListenerAdapter() {
        override fun onTransitionEnd(transition: Transition?) {
          refresher.hide(isGone = true)
        }
      })
      refresher.hide(isGone = true)
    }
  }

  private fun getTransition(@TransitionRes transitionId: Int): Transition {
    val inflater = TransitionInflater.from(activity)
    return inflater.inflateTransition(transitionId)
  }

  fun onResult() {
    TransitionManager.beginDelayedTransition(refresher,
        getTransition(R.transition.search_show_result))
    refresher.show()
  }

  override fun message(resource: Int) {
    showMessage(resource)
  }

  override fun showLoading() {
    progressBar.show()
  }

  override fun hideLoading() {
    progressBar.hide(isGone = true)
  }

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?)
      : View?
      = inflater?.inflate(R.layout.fragment_search, container, false)

  abstract fun inject()
}