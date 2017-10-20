package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.interactor.utils.MediaPage
import rx.Single

interface PagerInteractor<T>{
    fun fetchPage(page: MediaPage): Single<T>

    fun fetch(current:Int, limit:Int)=fetchPage(MediaPage(current,limit))
}