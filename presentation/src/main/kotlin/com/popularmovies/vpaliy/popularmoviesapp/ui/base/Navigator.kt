package com.popularmovies.vpaliy.popularmoviesapp.ui.base

import android.app.Activity
import android.content.Intent
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.view.View
import com.popularmovies.vpaliy.domain.entity.MediaType
import com.popularmovies.vpaliy.popularmoviesapp.App
import com.popularmovies.vpaliy.popularmoviesapp.ui.detail.DetailActivity
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaModel
import com.popularmovies.vpaliy.popularmoviesapp.ui.more.MoreActivity
import com.popularmovies.vpaliy.popularmoviesapp.ui.search.SearchActivity

class Navigator {

  fun navigateToMore(activity: Activity, type: MediaType) {
    App.pushInjector(MoreActivity.buildInjector(type))
    activity.startActivity(Intent(activity, MoreActivity::class.java))
  }

  fun navigateToSearch(activity: Activity, pair: Pair<View, String>) {
    val intent = Intent(activity, SearchActivity::class.java)
    val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pair)
    activity.startActivity(intent, optionsCompat.toBundle())
  }

  fun navigateToDetails(activity: Activity, model: MediaModel) {
    App.pushInjector(DetailActivity.buildInjector(model))
    activity.startActivity(Intent(activity, DetailActivity::class.java))
  }
}