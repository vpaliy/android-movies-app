package com.popularmovies.vpaliy.popularmoviesapp.ui.search

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.popularmovies.vpaliy.popularmoviesapp.R
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseAdapter
import kotlinx.android.synthetic.main.fragment_search.*

class SearchResult<T>:Fragment(){

    var adapter:BaseAdapter<T>?=null
        set(value) {
            field=value
            field?.let {
                result.adapter=it
            }
        }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?)
            :View?
            =inflater?.inflate(R.layout.fragment_search,container,false)
}