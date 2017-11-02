package com.popularmovies.vpaliy.popularmoviesapp.ui.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.popularmovies.vpaliy.domain.entity.MovieType
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.Media
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.ViewWrapper
import javax.inject.Inject

abstract class HomeFragment:Fragment(),HomeContract.View{

    lateinit var presenter:HomeContract.Presenter

    private val adapter by lazy { HomeAdapter(context) }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        inject()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun show(data: List<Media>, type: MovieType) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun error() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun empty() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun message(resource: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    abstract fun types():Array<MovieType>
    abstract fun inject()
}