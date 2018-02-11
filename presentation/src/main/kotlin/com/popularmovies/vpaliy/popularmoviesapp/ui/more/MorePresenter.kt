package com.popularmovies.vpaliy.popularmoviesapp.ui.more

import com.popularmovies.vpaliy.data.mapper.Mapper
import com.popularmovies.vpaliy.domain.entity.MediaType
import com.popularmovies.vpaliy.domain.entity.Popular
import com.popularmovies.vpaliy.domain.interactor.GetPage
import com.popularmovies.vpaliy.domain.interactor.params.TypePage
import com.popularmovies.vpaliy.popularmoviesapp.R
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaModel
import com.popularmovies.vpaliy.popularmoviesapp.ui.more.MoreContract.View
import com.popularmovies.vpaliy.popularmoviesapp.ui.more.MoreContract.Presenter
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope
import com.popularmovies.vpaliy.popularmoviesapp.ui.reflect

@ViewScope
class MorePresenter<T>(private val interactor: GetPage<T>,
                       private val mapper: Mapper<MediaModel, T>,
                       private val type: MediaType= Popular) : Presenter {

  private lateinit var view: View
  private val page by lazy { TypePage(type) }

  override fun attachView(view: View) {
    this.view = view
  }

  override fun attachType(type: MediaType) {
  }

  override fun more() {
    view.showLoading()
    page.next()
    interactor.execute(mapper.reflect(this::onSuccess), this::onError, page)
  }

  override fun start() {
    page.current = 1
    view.showLoading()
    interactor.execute(mapper.reflect(this::onSuccess), this::onError, page)
  }

  private fun onSuccess(page: TypePage?, data: List<MediaModel>) {
    view.hideLoading()
    page?.let {
      if (data.isNotEmpty()) {
        if (page.current > 1)
          view.append(data)
        else
          view.show(data)
      } else view.empty()
    }
  }

  private fun onError(ex: Throwable) {
    ex.printStackTrace()
    view.hideLoading()
    view.error(R.string.data_error)
  }
}