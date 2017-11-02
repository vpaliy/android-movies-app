package com.popularmovies.vpaliy.popularmoviesapp.ui.home

import com.popularmovies.vpaliy.domain.entity.MediaType
import javax.inject.Inject

class TVFragment:HomeFragment(){
    override fun types()=arrayOf(MediaType.UPCOMING, MediaType.TOP, MediaType.POPULAR)

    override fun getColor(type: MediaType): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTitle(type: MediaType): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @Inject
    fun attach(presenter:HomeContract.Presenter){
        this.presenter=presenter
    }

    override fun inject() {

    }
}