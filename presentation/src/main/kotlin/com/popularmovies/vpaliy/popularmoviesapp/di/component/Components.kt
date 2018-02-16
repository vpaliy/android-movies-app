package com.popularmovies.vpaliy.popularmoviesapp.di.component

import com.popularmovies.vpaliy.popularmoviesapp.di.module.DetailsModule
import com.popularmovies.vpaliy.popularmoviesapp.di.module.MovieMoreModule
import com.popularmovies.vpaliy.popularmoviesapp.di.module.TVMoreModule
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope
import com.popularmovies.vpaliy.popularmoviesapp.ui.detail.DetailActivity
import com.popularmovies.vpaliy.popularmoviesapp.ui.more.MoreActivity
import dagger.Component

@ViewScope
@Component(dependencies = arrayOf(ApplicationComponent::class),
    modules = arrayOf(MovieMoreModule::class))
interface MoreMoviesComponent : BaseComponent<MoreActivity>

@ViewScope
@Component(dependencies = arrayOf(ApplicationComponent::class),
    modules = arrayOf(TVMoreModule::class))
interface MoreTVComponent : BaseComponent<MoreActivity>

@ViewScope
@Component(dependencies = arrayOf(ApplicationComponent::class),
    modules = arrayOf(DetailsModule::class))
interface DetailsComponent : BaseComponent<DetailActivity>