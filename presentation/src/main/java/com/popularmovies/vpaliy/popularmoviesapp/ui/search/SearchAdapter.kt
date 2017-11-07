package com.popularmovies.vpaliy.popularmoviesapp.ui.search

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class SearchAdapter(manager:FragmentManager)
    :FragmentPagerAdapter(manager),QueryListener{

    private val listeners= mutableListOf<QueryListener>()

    override fun getItem(position: Int):Fragment{
        val fragment=when(position){
            0-> MovieResult()
            1-> TVResult()
            else -> PeopleResult()
        }
        listeners.add(fragment)
        return fragment
    }

    override fun getCount()=3

    override fun getPageTitle(position: Int)=when(position){
        0->"Movies"
        1->"TV"
        else->"People"
    }

    override fun queryTyped(query: String){
        listeners.forEach { it.queryTyped(query) }
    }
}