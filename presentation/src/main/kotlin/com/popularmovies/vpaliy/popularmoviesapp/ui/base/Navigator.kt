package com.popularmovies.vpaliy.popularmoviesapp.ui.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.view.View
import com.popularmovies.vpaliy.popularmoviesapp.ui.detail.DetailActivity
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaModel
import com.popularmovies.vpaliy.popularmoviesapp.ui.more.MoreActivity
import com.popularmovies.vpaliy.popularmoviesapp.ui.search.SearchActivity
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.EXTRA_ID

class Navigator {
  fun navigateToMore(activity: Activity, bundle: Bundle) {
    val intent = Intent(activity, MoreActivity::class.java).putExtras(bundle)
    activity.startActivity(intent)
  }

  fun navigateToSearch(activity: Activity, pair: Pair<View, String>) {
    val intent = Intent(activity, SearchActivity::class.java)
    val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pair)
    activity.startActivity(intent, optionsCompat.toBundle())
  }

  fun navigateToDetails(activity: Activity, model: MediaModel) {
    val intent = Intent(activity, DetailActivity::class.java)
    intent.putExtra(EXTRA_ID, model.id)
    activity.startActivity(intent)
  }
}