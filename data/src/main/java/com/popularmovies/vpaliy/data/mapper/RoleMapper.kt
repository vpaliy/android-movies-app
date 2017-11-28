package com.popularmovies.vpaliy.data.mapper

import com.popularmovies.vpaliy.domain.entity.Role
import com.vpaliy.tmdb.model.CastModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoleMapper @Inject constructor():Mapper<Role,CastModel> {

  override fun map(fake: CastModel) = Role(fake.credit_id, fake.name, fake.profile_path, fake.character)

  override fun reverse(real: Role) = CastModel(real.id.toInt(), real.role, real.id, 0,
          real.id.toInt(), real.actor, 0, real.picture)
}