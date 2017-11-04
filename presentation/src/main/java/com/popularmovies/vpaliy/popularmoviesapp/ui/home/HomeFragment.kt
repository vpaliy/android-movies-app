package com.popularmovies.vpaliy.popularmoviesapp.ui.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.popularmovies.vpaliy.domain.entity.MediaType
import com.popularmovies.vpaliy.popularmoviesapp.R
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaModel
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.ViewWrapper
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

abstract class HomeFragment:Fragment(),HomeContract.View{

    abstract var presenter:HomeContract.Presenter?

    private val adapter by lazy { HomeAdapter(context) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list.adapter=adapter
    }

    override fun onStart() {
        super.onStart()
        presenter?.start(types())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            :View?
            =inflater.inflate(R.layout.fragment_home,container,false)

    override fun show(data: List<MediaModel>, type: MediaType) {
        val mediaAdapter=MediaAdapter(context,data.toMutableList())
        adapter.add(ViewWrapper(mediaAdapter,getTitle(type),getColor(type)))
    }

    override fun error() {
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