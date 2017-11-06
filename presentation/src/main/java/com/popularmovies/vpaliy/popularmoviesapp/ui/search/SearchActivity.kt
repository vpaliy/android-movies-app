package com.popularmovies.vpaliy.popularmoviesapp.ui.search

import android.os.Bundle
import com.popularmovies.vpaliy.popularmoviesapp.App
import com.popularmovies.vpaliy.popularmoviesapp.R
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseActivity

class SearchActivity:BaseActivity(){

    private val adapter by lazy { SearchAdapter(supportFragmentManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
    }

    override fun inject() {
        App.component?.inject(this)
    }
}