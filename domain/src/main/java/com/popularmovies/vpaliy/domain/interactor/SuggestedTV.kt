package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.entity.MediaSet
import com.popularmovies.vpaliy.domain.entity.TVShow
import com.popularmovies.vpaliy.domain.executor.BaseScheduler
import com.popularmovies.vpaliy.domain.interactor.utils.Consumer
import com.popularmovies.vpaliy.domain.interactor.utils.SimilarPage
import com.popularmovies.vpaliy.domain.repository.Repository
import javax.inject.Inject

class SuggestedTV @Inject
constructor(val repository: Repository<TVShow>, scheduler: BaseScheduler)
    :PagerInteractor<TVShow,SimilarPage>(scheduler){
    override fun fetchPage(consumer: Consumer<MediaSet<TVShow>>, params: SimilarPage) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}