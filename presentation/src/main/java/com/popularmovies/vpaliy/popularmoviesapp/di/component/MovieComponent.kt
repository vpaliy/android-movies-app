package com.popularmovies.vpaliy.popularmoviesapp.di.component

import com.popularmovies.vpaliy.popularmoviesapp.di.module.MovieModule
import com.popularmovies.vpaliy.popularmoviesapp.ui.pager.PagerActivity
import com.popularmovies.vpaliy.popularmoviesapp.ui.home.MoviesFragment
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope
import dagger.Component

@ViewScope
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(MovieModule::class))
interface MovieComponent {
    fun inject(fragment:MoviesFragment)
    fun inject(activity:PagerActivity)
}
