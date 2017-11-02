package com.popularmovies.vpaliy.popularmoviesapp.ui.home

import com.popularmovies.vpaliy.domain.entity.MovieType
import javax.inject.Inject

class MoviesFragment:HomeFragment(){
    override fun types()= arrayOf(MovieType.POPULAR)

    @Inject
    fun attach(presenter: HomeContract.Presenter){
        this.presenter=presenter
    }

    override fun inject() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}