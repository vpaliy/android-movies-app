package com.popularmovies.vpaliy.data.mapper

import com.popularmovies.vpaliy.domain.entity.Trailer
import com.vpaliy.tmdb.model.VideoModel
import javax.inject.Singleton

@Singleton
class TrailerMapper:Mapper<Trailer, VideoModel>{

    override fun map(fake: VideoModel)=Trailer(fake.site,fake.id,fake.name)

    override fun reverse(real: Trailer): VideoModel {
        val model=VideoModel()
        model.id=real.id
        model.site=real.video
        model.name=real.title
        return model
    }
}