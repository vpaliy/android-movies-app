package com.popularmovies.vpaliy.popularmoviesapp.ui.home

import com.popularmovies.vpaliy.domain.entity.MovieType
import javax.inject.Inject

class TVFragment:HomeFragment(){
    override fun types()=arrayOf(MovieType.UPCOMING,MovieType.TOP,MovieType.POPULAR)

    @Inject
    fun attach(presenter:HomeContract.Presenter){
        this.presenter=presenter
    }

    override fun inject() {

    }
}