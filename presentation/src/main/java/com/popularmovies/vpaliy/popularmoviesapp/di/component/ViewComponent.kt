package com.popularmovies.vpaliy.popularmoviesapp.di.component

import com.popularmovies.vpaliy.popularmoviesapp.di.module.PresenterModule
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope
import com.popularmovies.vpaliy.popularmoviesapp.ui.additional.PagerActivity
import com.popularmovies.vpaliy.popularmoviesapp.ui.home.MoviesFragment
import com.popularmovies.vpaliy.popularmoviesapp.ui.home.TVFragment
import dagger.Component

@ViewScope
@Component(dependencies = arrayOf(ApplicationComponent::class),
        modules = arrayOf(PresenterModule::class))
interface ViewComponent {
    fun inject(fragment:MoviesFragment)
    fun inject(fragment:TVFragment)
    fun inject(activity:PagerActivity)
}
