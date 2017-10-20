package com.popularmovies.vpaliy.data.repository

import com.popularmovies.vpaliy.data.entity.TvShow
import com.popularmovies.vpaliy.domain.entity.*
import com.popularmovies.vpaliy.domain.repository.Repository
import rx.Single

class TVRepository:Repository<TvShow>{

    override fun fetchItem(type: MovieType): Single<TvShow> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchList(type: MovieType): Single<MediaSet<TvShow>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchBasedOn(item: TvShow, type: SimilarityType): Single<List<TvShow>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchReviews(item: TvShow): Single<List<Review>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchRoles(item: TvShow): Single<List<Role>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchTrailers(item: TvShow): Single<List<Trailer>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}