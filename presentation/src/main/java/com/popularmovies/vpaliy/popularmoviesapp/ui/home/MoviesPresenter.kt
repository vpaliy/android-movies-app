package com.popularmovies.vpaliy.popularmoviesapp.ui.home

import com.popularmovies.vpaliy.data.mapper.Mapper
import com.popularmovies.vpaliy.domain.entity.Movie
import com.popularmovies.vpaliy.domain.interactor.RequestInteractor
import com.popularmovies.vpaliy.domain.interactor.params.TypePage
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaModel
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope

@ViewScope
class MoviesPresenter(request:RequestInteractor<TypePage,List<Movie>>, mapper:Mapper<MediaModel,Movie>)
    :HomePresenter<Movie>(request,mapper)