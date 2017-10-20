package com.popularmovies.vpaliy.domain.interactor

import com.popularmovies.vpaliy.domain.entity.MediaSet
import com.popularmovies.vpaliy.domain.entity.Movie
import com.popularmovies.vpaliy.domain.executor.BaseScheduler
import com.popularmovies.vpaliy.domain.interactor.utils.Consumer
import com.popularmovies.vpaliy.domain.interactor.utils.SimilarPage
import com.popularmovies.vpaliy.domain.repository.Repository
import javax.inject.Inject

class SuggestedMovies @Inject
constructor(val repository: Repository<Movie>, scheduler: BaseScheduler)
    :PagerInteractor<Movie,SimilarPage>(scheduler){

    override fun fetchPage(consumer: Consumer<MediaSet<Movie>>, params: SimilarPage) {

    }
}