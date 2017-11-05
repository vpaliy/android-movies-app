package com.popularmovies.vpaliy.popularmoviesapp.ui.search

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.popularmovies.vpaliy.domain.entity.Actor
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseAdapter
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaModel
import com.popularmovies.vpaliy.popularmoviesapp.ui.more.MediaAdapter

class SearchAdapter(manager:FragmentManager):FragmentPagerAdapter(manager){

    private val movieResult by lazy { SearchResult<MediaModel>() }
    private val tvResult by lazy { SearchResult<MediaModel>() }
    private val peopleResult by lazy { SearchResult<Actor>() }

    override fun getItem(position: Int)=when(position){
        0->movieResult
        1->tvResult
        else->peopleResult
    }

    override fun getCount()=3

    fun appendMovies(data:List<MediaModel>){
        movieResult.adapter?.append(data)
    }

    fun appendTV(data:List<MediaModel>){
        tvResult.adapter?.append(data)
    }

    fun appendPeople(data:List<Actor>){
        peopleResult.adapter?.append(data)
    }

    fun showMovies(adapter:BaseAdapter<MediaModel>){
        movieResult.adapter=adapter
    }

    fun showTV(adapter:BaseAdapter<MediaModel>){
        tvResult.adapter=adapter
    }

}