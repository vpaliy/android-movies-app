package com.popularmovies.vpaliy.popularmoviesapp.ui.more

import android.os.Bundle
import com.google.gson.reflect.TypeToken
import com.popularmovies.vpaliy.domain.entity.MediaType
import com.popularmovies.vpaliy.popularmoviesapp.App
import com.popularmovies.vpaliy.popularmoviesapp.R
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerMovieComponent
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerTVComponent
import com.popularmovies.vpaliy.popularmoviesapp.di.module.MovieModule
import com.popularmovies.vpaliy.popularmoviesapp.di.module.TVModule
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseActivity
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaModel
import com.popularmovies.vpaliy.popularmoviesapp.ui.more.MoreContract.Presenter
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.*
import kotlinx.android.synthetic.main.activity_more.*
import javax.inject.Inject

class MoreActivity :BaseActivity(), MoreContract.View{

  internal var presenter:Presenter?=null
    @Inject set(value) {
      field=value
      field?.attachView(this)
    }

  private val adapter:MediaAdapter by lazy { MediaAdapter(this,{}) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_more)
    setSupportActionBar(toolbar)
    start()
  }

  private fun start(){
    val type=intent.extras.fetchHeavyObject<MediaType>(EXTRA_TYPE, object:TypeToken<MediaType>(){}.type)
    presenter?.let {
      it.attachType(type!!)
      it.start()
      refresher.setOnRefreshListener(it::start)
      list.addOnScrollListener(object:OnReachBottomListener(list.layoutManager){
        override fun onLoadMore()=it.more()
      })
    }
  }

  override fun append(data: List<MediaModel>)= adapter.append(data)

  override fun empty() {}

  override fun error() {}

  override fun message(resource: Int) {}

  override fun show(data: List<MediaModel>) {
    if(list.adapter!==adapter) list.adapter=adapter
    adapter.data=data.toMutableList()
  }

  override fun showLoading() {
    refresher.isRefreshing=true
  }

  override fun hideLoading() {
    refresher.isRefreshing=false
  }

  //TODO fix that :(
  override fun inject() {
    val isMovies=intent.getBooleanExtra(EXTRA_IS_MOVIES,false)
    if(!isMovies){
      DaggerTVComponent.builder()
              .tVModule(TVModule())
              .applicationComponent(App.component)
              .build().inject(this)
    }else{
      DaggerMovieComponent.builder()
              .applicationComponent(App.component)
              .movieModule(MovieModule())
              .build().inject(this)
    }
  }
}