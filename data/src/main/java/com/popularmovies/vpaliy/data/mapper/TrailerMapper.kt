package com.popularmovies.vpaliy.data.mapper

import com.popularmovies.vpaliy.domain.entity.Trailer
import com.vpaliy.tmdb.model.VideoModel

class TrailerMapper:Mapper<Trailer, VideoModel>{

    override fun map(fake: VideoModel): Trailer {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun reverse(real: Trailer): VideoModel {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}