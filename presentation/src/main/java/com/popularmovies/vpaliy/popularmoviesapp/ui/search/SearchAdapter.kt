package com.popularmovies.vpaliy.popularmoviesapp.ui.search

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.popularmovies.vpaliy.domain.entity.Actor
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseAdapter
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaModel

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

    fun appendPeople(data:List<Actor>){
        peopleResult.adapter?.append(data)
    }

    fun appendMedia(data:List<MediaModel>){

    }

    fun showMedia(adapter:BaseAdapter<*>){

    }

}