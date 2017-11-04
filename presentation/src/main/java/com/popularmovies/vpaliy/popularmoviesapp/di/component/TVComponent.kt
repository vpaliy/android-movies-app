package com.popularmovies.vpaliy.popularmoviesapp.di.component

import com.popularmovies.vpaliy.popularmoviesapp.di.module.TVModule
import com.popularmovies.vpaliy.popularmoviesapp.ui.pager.PagerActivity
import com.popularmovies.vpaliy.popularmoviesapp.ui.home.TVFragment
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope
import dagger.Component

@ViewScope
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(TVModule::class))
interface TVComponent {
    fun inject(fragment:TVFragment)
    fun inject(activity:PagerActivity)
}
