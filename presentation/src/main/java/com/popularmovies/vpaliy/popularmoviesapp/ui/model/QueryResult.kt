package com.popularmovies.vpaliy.popularmoviesapp.ui.model

import com.popularmovies.vpaliy.domain.interactor.params.SearchPage

data class QueryResult<out T>(val page:SearchPage, val type:SearchType, val data:List<T>){
    val isFirst
        get() = page.current > 1
}