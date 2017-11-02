package com.popularmovies.vpaliy.popularmoviesapp.ui.mapper

import com.popularmovies.vpaliy.data.mapper.Mapper
import com.popularmovies.vpaliy.domain.entity.Movie
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaModel

class MediaMovieMapper :Mapper<MediaModel,Movie>{
    override fun map(fake: Movie): MediaModel {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun reverse(real: MediaModel): Movie {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}