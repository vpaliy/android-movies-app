package com.popularmovies.vpaliy.data.mapper

import com.popularmovies.vpaliy.data.entity.TVEntity
import com.popularmovies.vpaliy.domain.entity.TVShow

class TVMapper:Mapper<TVShow,TVEntity>{

    override fun map(fake: TVEntity): TVShow {
        val tv=TVShow()
        val entity=fake.details
        tv.images=fake.images
        tv.genres=fake.genres
        entity?.let {
            tv.id=it.id.toString()
            tv.description=it.overview
            tv.averageVote=it.vote_average.toDouble()
            tv.backdropImage=it.backdrop_path
            tv.poster=it.poster_path
            tv.firstAirTime=it.first_air_date
            tv.lastAirTime=it.last_air_date
            tv.title=it.name
        }
        return tv
    }

    override fun reverse(real: TVShow): TVEntity {
        val entity=TVEntity()
        entity.images=real.images
        entity.genres=real.genres
        //TODO fix TV details entity
        return entity
    }
}