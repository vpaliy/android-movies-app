package com.popularmovies.vpaliy.popularmoviesapp.ui.search

import android.os.Bundle
import com.popularmovies.vpaliy.domain.entity.Actor
import com.popularmovies.vpaliy.domain.entity.Movie
import com.popularmovies.vpaliy.domain.entity.TVShow
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseActivity
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.SearchType
import com.popularmovies.vpaliy.popularmoviesapp.ui.search.SearchContract.Presenter

class SearchActivity:BaseActivity(),SearchContract.View{

    lateinit var presenter:Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun appendMovies(data: List<Movie>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun appendPeople(data: List<Actor>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun appendTV(data: List<TVShow>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun empty(type: SearchType) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun error(type: SearchType) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMovies(data: List<Movie>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showPeople(data: List<Actor>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showTV(data: List<TVShow>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun inject() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}