package com.popularmovies.vpaliy.popularmoviesapp.ui.more

import android.os.Bundle
import com.popularmovies.vpaliy.popularmoviesapp.App
import com.popularmovies.vpaliy.popularmoviesapp.R
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseActivity
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaModel
import com.popularmovies.vpaliy.popularmoviesapp.ui.more.MoreContract.Presenter
import kotlinx.android.synthetic.main.activity_more.*
import android.support.annotation.StringRes
import android.view.View
import com.popularmovies.vpaliy.domain.entity.MediaType
import com.popularmovies.vpaliy.popularmoviesapp.di.component.BaseComponent
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerMoreMoviesComponent
import com.popularmovies.vpaliy.popularmoviesapp.di.module.MovieMoreModule
import com.popularmovies.vpaliy.popularmoviesapp.di.injector.Injector
import com.popularmovies.vpaliy.popularmoviesapp.ui.showErrorMessage
import com.popularmovies.vpaliy.popularmoviesapp.ui.showMessage
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.getStatusBarHeight
import javax.inject.Inject

class MoreActivity : BaseActivity(), MoreContract.View {
  internal var presenter: Presenter? = null
    @Inject set(value) {
      field = value
      field?.attachView(this)
    }

  private val adapter: MediaAdapter by lazy {
    MediaAdapter(this, {
      navigator.navigateToDetails(this, it)
    })
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_more)
    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    setupActionBar()
    presenter?.start()
    refresher.setOnRefreshListener {
      presenter?.start()
    }
  }

  override fun append(data: List<MediaModel>) = adapter.append(data)

  override fun empty() {
    TODO("Show empty icon")
  }

  override fun error(@StringRes resource: Int) {
    rootView.showErrorMessage(resource)
  }

  override fun message(resource: Int) {
    rootView.showMessage(resource)
  }

  override fun show(data: List<MediaModel>) {
    if (list.adapter != adapter)
      list.adapter = adapter
    adapter.data = data.toMutableList()
  }

  override fun showLoading() {
    refresher.isRefreshing = true
  }

  override fun hideLoading() {
    refresher.isRefreshing = false
  }

  override fun inject() {
    App.inject(this)
  }

  override fun showTitle(resource: Int) {
    setTitle(resource)
  }

  private fun setupActionBar() {
    val statusBarHeight = getStatusBarHeight(resources)
    toolbar.layoutParams.height += statusBarHeight
    toolbar.setPadding(0, statusBarHeight, 0, 0)
    setSupportActionBar(toolbar)
    supportActionBar?.let {
      it.setHomeButtonEnabled(true)
      it.setDisplayHomeAsUpEnabled(true)
    }
  }

  companion object {
    fun buildInjector(type: MediaType) = object : Injector<MoreActivity> {
      override val component: BaseComponent<MoreActivity> by lazy {
        DaggerMoreMoviesComponent.builder()
            .applicationComponent(App.component)
            .movieMoreModule(MovieMoreModule(type))
            .build()
      }

      override fun inject(target: Any) {
        if (target is MoreActivity)
          component.inject(target)
      }
    }
  }
}