package com.popularmovies.vpaliy.popularmoviesapp.di.component

import com.popularmovies.vpaliy.popularmoviesapp.di.module.SearchModule
import com.popularmovies.vpaliy.popularmoviesapp.ui.search.movies.MovieResult
import com.popularmovies.vpaliy.popularmoviesapp.ui.search.people.PeopleResult
import com.popularmovies.vpaliy.popularmoviesapp.ui.search.shows.TVResult
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope
import dagger.Component

@ViewScope
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(SearchModule::class))
interface SearchComponent {
  fun inject(result: MovieResult)
  fun inject(result: TVResult)
  fun inject(result: PeopleResult)
}