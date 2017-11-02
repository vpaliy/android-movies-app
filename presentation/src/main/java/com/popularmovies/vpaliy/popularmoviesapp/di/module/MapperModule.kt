package com.popularmovies.vpaliy.popularmoviesapp.di.module

import com.popularmovies.vpaliy.data.entity.MovieEntity
import com.popularmovies.vpaliy.data.entity.TVEntity
import com.popularmovies.vpaliy.data.mapper.*
import com.popularmovies.vpaliy.domain.entity.*
import com.popularmovies.vpaliy.popularmoviesapp.ui.mapper.MediaMovieMapper
import com.popularmovies.vpaliy.popularmoviesapp.ui.mapper.MediaTVMappper
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaModel
import com.vpaliy.tmdb.model.ActorModel
import com.vpaliy.tmdb.model.CastModel
import com.vpaliy.tmdb.model.ReviewModel
import com.vpaliy.tmdb.model.VideoModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MapperModule{
    @Singleton
    @Provides
    internal fun movieMapper(mapper:MovieMapper): Mapper<Movie, MovieEntity> =mapper

    @Singleton
    @Provides
    internal fun tvMapper(mapper:TVMapper):Mapper<TVShow,TVEntity> =mapper

    @Singleton
    @Provides
    internal fun actorMapper(mapper:ActorMapper):Mapper<Actor,ActorModel> =mapper

    @Singleton
    @Provides
    internal fun roleMapper(mapper:RoleMapper):Mapper<Role,CastModel> =mapper

    @Singleton
    @Provides
    internal fun trailerMapper(mapper:TrailerMapper):Mapper<Trailer,VideoModel> =mapper

    @Singleton
    @Provides
    internal fun reviewMapper(mapper:ReviewMapper):Mapper<Review,ReviewModel> =mapper

    @Singleton
    @Provides
    internal fun mediaMovieMapper(mapper:MediaMovieMapper):Mapper<MediaModel,Movie> = mapper

    @Singleton
    @Provides
    internal fun mediaTVMapper(mapper:MediaTVMappper):Mapper<MediaModel,TVShow> = mapper
}
