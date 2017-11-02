package com.popularmovies.vpaliy.popularmoviesapp.ui.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.popularmovies.vpaliy.domain.entity.MediaType
import com.popularmovies.vpaliy.popularmoviesapp.R
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaModel
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.ViewWrapper

abstract class HomeFragment:Fragment(),HomeContract.View{

    lateinit var presenter:HomeContract.Presenter

    private val adapter by lazy { HomeAdapter(context) }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inject()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            :View?
            =inflater.inflate(R.layout.fragment_home,container,false)

    override fun show(data: List<MediaModel>, type: MediaType) {
        val mediaAdapter=MediaAdapter(context,data.toMutableList())
        adapter.add(ViewWrapper(mediaAdapter,getTitle(type),getColor(type)))
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

    abstract fun getTitle(type:MediaType):String
    abstract fun getColor(type:MediaType):Int
    abstract fun types():Array<MediaType>
    abstract fun inject()
}