package com.popularmovies.vpaliy.domain.repository

import com.popularmovies.vpaliy.domain.entity.Actor
import com.popularmovies.vpaliy.domain.interactor.params.Stream

interface Repository {
    fun fetchActor(id:String): Stream<String, Actor>
}