package com.popularmovies.vpaliy.popularmoviesapp.ui.search

import android.os.Bundle
import com.popularmovies.vpaliy.domain.entity.Actor
import com.popularmovies.vpaliy.popularmoviesapp.R
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseActivity
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaModel
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.SearchType
import com.popularmovies.vpaliy.popularmoviesapp.ui.search.SearchContract.Presenter

class SearchActivity:BaseActivity(),SearchContract.View {

    lateinit var presenter: Presenter

    private val adapter by lazy { SearchAdapter(supportFragmentManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
    }

    override fun appendMedia(type: SearchType, data: List<MediaModel>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun appendPeople(data: List<Actor>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun empty(type: SearchType) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun error(type: SearchType) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMedia(type: SearchType, data: List<MediaModel>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showPeople(data: List<Actor>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun inject() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}