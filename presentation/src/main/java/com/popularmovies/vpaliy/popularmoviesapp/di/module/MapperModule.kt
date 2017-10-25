package com.popularmovies.vpaliy.popularmoviesapp.di.module

import com.popularmovies.vpaliy.data.entity.MovieEntity
import com.popularmovies.vpaliy.data.entity.TVEntity
import com.popularmovies.vpaliy.data.mapper.*
import com.popularmovies.vpaliy.domain.entity.*
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
    internal fun mapper(mapper:MovieMapper): Mapper<Movie, MovieEntity> =mapper

    @Singleton
    @Provides
    internal fun mapper(mapper:TVMapper):Mapper<TVShow,TVEntity> =mapper

    @Singleton
    @Provides
    internal fun mapper(mapper:ActorMapper):Mapper<Actor,ActorModel> =mapper

    @Singleton
    @Provides
    internal fun mapper(mapper:RoleMapper):Mapper<Role,CastModel> =mapper

    @Singleton
    @Provides
    internal fun mapper(mapper:TrailerMapper):Mapper<Trailer,VideoModel> =mapper

    @Singleton
    @Provides
    internal fun mapper(mapper:ReviewMapper):Mapper<Review,ReviewModel> =mapper
}
