package com.popularmovies.vpaliy.data.repository

import com.popularmovies.vpaliy.domain.entity.Review
import com.popularmovies.vpaliy.domain.entity.Role
import com.popularmovies.vpaliy.domain.entity.TVShow
import com.popularmovies.vpaliy.domain.entity.Trailer
import com.popularmovies.vpaliy.domain.interactor.params.Stream
import com.popularmovies.vpaliy.domain.interactor.params.Suggestion
import com.popularmovies.vpaliy.domain.interactor.params.TypePage
import com.popularmovies.vpaliy.domain.repository.MediaRepository

class TVRepository:MediaRepository<TVShow>{
    override fun fetchItem(id: String): Stream<String, TVShow> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchList(request: TypePage): Stream<TypePage, List<TVShow>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchReviews(item: TVShow): Stream<TVShow, List<Review>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchRoles(item: TVShow): Stream<TVShow, List<Role>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchSuggested(request: Suggestion<TVShow>): Stream<Suggestion<TVShow>, List<TVShow>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchTrailers(item: TVShow): Stream<TVShow, List<Trailer>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}