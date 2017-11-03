package com.popularmovies.vpaliy.popularmoviesapp.di.component

import com.popularmovies.vpaliy.popularmoviesapp.di.module.PresenterModule
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope
import com.popularmovies.vpaliy.popularmoviesapp.ui.home.HomeFragment
import dagger.Component

@ViewScope
@Component(dependencies = arrayOf(ApplicationComponent::class),
        modules = arrayOf(PresenterModule::class))
interface ViewComponent {
    fun inject(fragment:HomeFragment)
}
