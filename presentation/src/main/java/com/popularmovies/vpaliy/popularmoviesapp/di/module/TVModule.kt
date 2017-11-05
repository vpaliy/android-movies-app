package com.popularmovies.vpaliy.popularmoviesapp.di.module

import com.popularmovies.vpaliy.data.mapper.Mapper
import com.popularmovies.vpaliy.domain.entity.TVShow
import com.popularmovies.vpaliy.domain.interactor.GetPage
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope
import com.popularmovies.vpaliy.popularmoviesapp.ui.home.HomeContract
import com.popularmovies.vpaliy.popularmoviesapp.ui.home.HomePresenter
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaModel
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.PagerFacade
import com.popularmovies.vpaliy.popularmoviesapp.ui.more.MoreContract
import com.popularmovies.vpaliy.popularmoviesapp.ui.more.MorePresenter
import dagger.Module
import dagger.Provides

@Module
class TVModule{
    @ViewScope
    @Provides
    fun home(interactor:GetPage<TVShow>,mapper:Mapper<MediaModel,TVShow>)
            :HomeContract.Presenter{
        val facade=PagerFacade(interactor,mapper)
        return HomePresenter(facade)
    }

    @ViewScope
    @Provides
    fun more(interactor: GetPage<TVShow>, mapper: Mapper<MediaModel,TVShow>)
            : MoreContract.Presenter{
        val facade=PagerFacade(interactor,mapper)
        return MorePresenter(facade)
    }
}