package com.popularmovies.vpaliy.popularmoviesapp.ui.home

import com.popularmovies.vpaliy.data.mapper.Mapper
import com.popularmovies.vpaliy.domain.entity.Movie
import com.popularmovies.vpaliy.domain.interactor.RequestInteractor
import com.popularmovies.vpaliy.domain.interactor.params.TypePage
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.Media
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope
import javax.inject.Inject

@ViewScope
class MoviesPresenter @Inject
constructor(request:RequestInteractor<TypePage,List<Movie>>,
            mapper:Mapper<Media,Movie>) :HomePresenter<Movie>(request,mapper)