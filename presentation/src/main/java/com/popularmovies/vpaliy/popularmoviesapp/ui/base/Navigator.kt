package com.popularmovies.vpaliy.popularmoviesapp.ui.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.popularmovies.vpaliy.popularmoviesapp.ui.more.MoreActivity

class Navigator{
    fun navigateToMore(activity:Activity, bundle:Bundle){
        val intent=Intent(activity, MoreActivity::class.java).putExtras(bundle)
        activity.startActivity(intent)
    }
}