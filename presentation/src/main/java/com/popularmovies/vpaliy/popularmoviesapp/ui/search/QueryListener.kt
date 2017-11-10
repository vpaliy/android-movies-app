package com.popularmovies.vpaliy.popularmoviesapp.ui.search

interface QueryListener{
    fun queryTyped(query:String)
    fun onCleared()
}