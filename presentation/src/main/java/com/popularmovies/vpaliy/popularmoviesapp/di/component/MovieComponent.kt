package com.popularmovies.vpaliy.popularmoviesapp.di.component

import com.popularmovies.vpaliy.popularmoviesapp.di.module.MovieModule
import com.popularmovies.vpaliy.popularmoviesapp.ui.more.MoreActivity
import com.popularmovies.vpaliy.popularmoviesapp.ui.home.MoviesFragment
import com.popularmovies.vpaliy.popularmoviesapp.ui.detail.DetailActivity
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope
import dagger.Component

@ViewScope
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(MovieModule::class))
interface MovieComponent {
    fun inject(fragment:MoviesFragment)
    fun inject(activity: MoreActivity)
    fun inject(activity:DetailActivity)
}
