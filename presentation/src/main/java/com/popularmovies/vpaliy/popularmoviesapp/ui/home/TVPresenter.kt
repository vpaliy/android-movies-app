package com.popularmovies.vpaliy.popularmoviesapp.ui.home

import com.popularmovies.vpaliy.data.mapper.Mapper
import com.popularmovies.vpaliy.domain.entity.TVShow
import com.popularmovies.vpaliy.domain.interactor.RequestInteractor
import com.popularmovies.vpaliy.domain.interactor.params.TypePage
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.Media
import javax.inject.Inject

@ViewScope
class TVPresenter @Inject
constructor(request:RequestInteractor<TypePage,List<TVShow>>,
            mapper:Mapper<Media,TVShow>) :HomePresenter<TVShow>(request,mapper)