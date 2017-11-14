package com.popularmovies.vpaliy.domain.repository

import com.popularmovies.vpaliy.domain.entity.Actor
import io.reactivex.Single

interface Repository {
    fun fetchActor(id:String): Single<Actor>
}