package com.popularmovies.vpaliy.data.mapper

import com.popularmovies.vpaliy.domain.entity.Actor
import com.vpaliy.tmdb.model.ActorModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ActorMapper @Inject constructor():Mapper<Actor, ActorModel>{
    override fun map(fake: ActorModel): Actor {
        val actor=Actor()
        actor.avatarPath=fake.profile_path
        actor.bio=fake.biography
        actor.birthday=fake.birthday
        actor.deathday=fake.deathday
        actor.id=fake.id.toString()
        actor.name=fake.name
        return actor
    }

    override fun reverse(real: Actor): ActorModel {
        val model=ActorModel()
        model.biography=real.bio?:""
        model.birthday=real.birthday
        model.deathday=real.deathday
        model.id=real.id?.toInt()?:0
        model.profile_path=real.avatarPath
        return model
    }
}