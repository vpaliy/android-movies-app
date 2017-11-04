package com.popularmovies.vpaliy.popularmoviesapp.ui.additional

import android.os.Bundle
import com.popularmovies.vpaliy.popularmoviesapp.App
import com.popularmovies.vpaliy.popularmoviesapp.R
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerViewComponent
import com.popularmovies.vpaliy.popularmoviesapp.di.module.PresenterModule
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseActivity
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaModel
import javax.inject.Inject

class PagerActivity:BaseActivity(),PagerContract.View{

    @Inject lateinit var presenter:PagerContract.Presenter

    private val adapter:MediaAdapter by lazy { MediaAdapter(this,{}) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    override fun append(data: List<MediaModel>) {
        adapter.append(data)
    }

    override fun empty() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun error() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun message(resource: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showList(data: List<MediaModel>) {
        adapter.data=data.toMutableList()
    }

    override fun inject() {
        DaggerViewComponent.builder()
                .presenterModule(PresenterModule())
                .applicationComponent(App.component)
                .build().inject(this)
    }
}