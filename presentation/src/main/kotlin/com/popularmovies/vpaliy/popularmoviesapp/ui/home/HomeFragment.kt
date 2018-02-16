package com.popularmovies.vpaliy.popularmoviesapp.ui.home

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.popularmovies.vpaliy.domain.entity.MediaType
import com.popularmovies.vpaliy.popularmoviesapp.R
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.Navigator
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaModel
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.ViewWrapper
import com.popularmovies.vpaliy.popularmoviesapp.ui.showErrorMessage
import com.popularmovies.vpaliy.popularmoviesapp.ui.showMessage
import com.vpaliy.kotlin_extensions.then
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

abstract class HomeFragment : Fragment(), HomeContract.View {
  abstract var presenter: HomeContract.Presenter?

  @Inject lateinit var navigator: Navigator

  private val adapter by lazy(LazyThreadSafetyMode.NONE) {
    HomeAdapter(context, this::showMore).apply {
      request = { presenter?.more(it) }
    }
  }

  private val dataHandler by lazy { Handler() }

  private val adapterMap by lazy(LazyThreadSafetyMode.NONE) { HashMap<MediaType, MediaAdapter>() }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    inject()
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    list.adapter = adapter
    list.itemAnimator = DefaultItemAnimator()
    refresher.setOnRefreshListener {
      presenter?.start(types())
    }
  }

  override fun onStart() {
    super.onStart()
    presenter?.start(types())
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
      : View?
      = inflater.inflate(R.layout.fragment_home, container, false)

  override fun show(data: List<MediaModel>, type: MediaType) {
    //TODO animate it
    val mediaAdapter = MediaAdapter(context, this::click)
    mediaAdapter.data = data.toMutableList()
    adapterMap[type] = mediaAdapter
    adapter.add(ViewWrapper(mediaAdapter, getTitle(type), getColor(type), type))
  }

  override fun append(data: List<MediaModel>, type: MediaType) {
    adapterMap[type]?.append(data)
  }

  override fun error(resource: Int) {
    showErrorMessage(resource)
  }

  override fun empty() {
    TODO("not implemented") //To change body of ed functions use File | Settings | File Templates.
  }

  override fun message(resource: Int) {
    showMessage(resource)
  }

  override fun showLoading() {
    refresher.isRefreshing = true
  }

  override fun hideLoading() {
    refresher.isRefreshing = false
  }

  private fun click(item: MediaModel) {
    navigator.navigateToDetails(activity, item)
  }

  private fun showMore(type: MediaType) {
    navigator.navigateToMore(activity, type)
  }

  override fun clear() {
    adapter.clear()
  }

  abstract fun getTitle(type: MediaType): String
  abstract fun getColor(type: MediaType): Int
  abstract fun types(): Array<MediaType>
  abstract fun inject()
}