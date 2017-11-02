package com.popularmovies.vpaliy.popularmoviesapp.ui.mapper

import com.popularmovies.vpaliy.data.mapper.Mapper
import com.popularmovies.vpaliy.domain.entity.TVShow
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaModel


class MediaTVMappper:Mapper<MediaModel,TVShow>{
    override fun map(fake: TVShow): MediaModel {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun reverse(real: MediaModel): TVShow {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}