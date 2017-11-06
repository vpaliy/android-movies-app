package com.popularmovies.vpaliy.popularmoviesapp.ui.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.popularmovies.vpaliy.popularmoviesapp.ui.more.MoreActivity
import com.popularmovies.vpaliy.popularmoviesapp.ui.search.SearchActivity

class Navigator{
    fun navigateToMore(activity:Activity, bundle:Bundle){
        val intent=Intent(activity, MoreActivity::class.java).putExtras(bundle)
        activity.startActivity(intent)
    }

    fun navigateToSearch(activity:Activity){
        val intent=Intent(activity, SearchActivity::class.java)
        activity.startActivity(intent)
    }
}