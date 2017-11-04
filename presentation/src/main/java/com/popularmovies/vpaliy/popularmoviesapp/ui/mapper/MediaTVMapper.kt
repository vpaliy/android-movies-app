package com.popularmovies.vpaliy.popularmoviesapp.ui.mapper

import android.net.Uri
import com.popularmovies.vpaliy.data.mapper.Mapper
import com.popularmovies.vpaliy.domain.entity.TVShow
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MediaTVMapper @Inject constructor():Mapper<MediaModel,TVShow>{
    override fun map(fake: TVShow):MediaModel{
        val poster=fake.poster?: Uri.parse("R.drawable.popcorn").toString()
        val backdrop=fake.backdropImage?:poster
        val release=fake.releaseYear?:""
        val ratings=fake.averageVote.toString()
        val tags=fake.genres?: emptyList()
        return MediaModel(fake.id,poster,fake.title,backdrop,release,ratings,tags)
    }

    override fun reverse(real: MediaModel)=TVShow().apply {
        this.id=real.id
        this.backdropImage=real.backdrop
        this.poster=real.poster
        this.title=real.title
        this.genres=real.tags
        this.averageVote=real.ratings.toDoubleOrNull()
    }
}