package com.popularmovies.vpaliy.popularmoviesapp.ui.search

import android.content.Context
import android.graphics.Color
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.popularmovies.vpaliy.popularmoviesapp.R
import com.popularmovies.vpaliy.popularmoviesapp.ui.color
import com.popularmovies.vpaliy.popularmoviesapp.ui.view.ChipPagerAdapter
import com.popularmovies.vpaliy.popularmoviesapp.ui.view.ChipTab

class SearchAdapter(val context: Context, manager:FragmentManager)
    :ChipPagerAdapter(manager),QueryListener{

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
        1->"TV Shows"
        else->"People"
    }

    override fun queryTyped(query: String){
        listeners.forEach { it.queryTyped(query) }
    }

    override fun styleFor(position: Int): ChipTab.StyleBuilder {
        return ChipTab.StyleBuilder().apply {
            this.textColor=context.color(R.color.colorTvShows)
            this.background= Color.TRANSPARENT
            this.selectedBackgroundColor=textColor
            this.selectedTextColor=Color.WHITE
        }
    }
}