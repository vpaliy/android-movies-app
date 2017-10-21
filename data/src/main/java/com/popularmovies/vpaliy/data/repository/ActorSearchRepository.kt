package com.popularmovies.vpaliy.data.repository

import com.popularmovies.vpaliy.domain.entity.Actor
import com.popularmovies.vpaliy.domain.repository.SearchRepository
import io.reactivex.Single

class ActorSearchRepository:SearchRepository<Actor>{
    override fun query(query: String): Single<List<Actor>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun next(): Single<List<Actor>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}