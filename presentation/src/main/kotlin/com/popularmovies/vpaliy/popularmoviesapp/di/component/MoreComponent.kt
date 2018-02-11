package com.popularmovies.vpaliy.popularmoviesapp.di.component

import com.popularmovies.vpaliy.popularmoviesapp.di.module.MoreModule
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope
import com.popularmovies.vpaliy.popularmoviesapp.ui.more.MoreActivity
import dagger.Component

@ViewScope
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(MoreModule::class))
interface MoreComponent {
  fun inject(activity: MoreActivity)
}