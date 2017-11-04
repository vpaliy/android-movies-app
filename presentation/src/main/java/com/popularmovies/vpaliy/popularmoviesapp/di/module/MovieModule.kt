package com.popularmovies.vpaliy.popularmoviesapp.di.module

import com.popularmovies.vpaliy.data.mapper.Mapper
import com.popularmovies.vpaliy.domain.entity.Movie
import com.popularmovies.vpaliy.domain.interactor.GetPage
import com.popularmovies.vpaliy.popularmoviesapp.ui.home.HomeContract
import com.popularmovies.vpaliy.popularmoviesapp.ui.home.HomePresenter
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaModel
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.PagerFacade
import com.popularmovies.vpaliy.popularmoviesapp.ui.pager.PagerContract
import com.popularmovies.vpaliy.popularmoviesapp.ui.pager.PagerPresenter
import dagger.Module
import dagger.Provides
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope

@Module
class MovieModule{
    @ViewScope
    @Provides
    fun home(interactor: GetPage<Movie>, mapper: Mapper<MediaModel,Movie>)
            : HomeContract.Presenter{
        val facade= PagerFacade(interactor,mapper)
        return HomePresenter(facade)
    }

    @ViewScope
    @Provides
    fun more(interactor: GetPage<Movie>, mapper: Mapper<MediaModel,Movie>)
            :PagerContract.Presenter{
        val facade= PagerFacade(interactor,mapper)
        return PagerPresenter(facade)
    }
}