package com.popularmovies.vpaliy.popularmoviesapp.ui.home

import com.popularmovies.vpaliy.data.mapper.Mapper
import com.popularmovies.vpaliy.domain.entity.MediaType
import com.popularmovies.vpaliy.domain.interactor.GetPage
import com.popularmovies.vpaliy.domain.interactor.params.TypePage
import com.popularmovies.vpaliy.popularmoviesapp.R
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaModel
import com.popularmovies.vpaliy.popularmoviesapp.ui.home.HomeContract.View
import com.popularmovies.vpaliy.popularmoviesapp.ui.reflect

class HomePresenter<T>(private val interactor: GetPage<T>,
                       private val mapper: Mapper<MediaModel, T>) : HomeContract.Presenter {

  private val map = mutableMapOf<MediaType, TypePage>()
  private lateinit var view: View

  override fun attach(view: View) {
    this.view = view
  }

  override fun more(type: MediaType) {
    map[type]?.let {
      it.next()
      interactor.execute(mapper.reflect(this::onSuccess), this::onError, it)
    }
  }

  private fun onError(ex: Throwable) {
    ex.printStackTrace()
    view.hideLoading()
    view.error(R.string.data_error)
  }

  private fun onSuccess(page: TypePage, data: List<MediaModel>) {
    view.hideLoading()
    if (data.isNotEmpty()) {
      if (page.current > 1)
        view.append(data, page.type)
      else
        view.show(data, page.type)
    } else view.empty()
  }

  override fun start(types: Array<MediaType>) {
    view.showLoading()
    types.forEach {
      map[it] = TypePage(it)
      interactor.execute(mapper.reflect(this::onSuccess), this::onError, map[it])
    }
  }

  override fun stop() = map.clear()
}