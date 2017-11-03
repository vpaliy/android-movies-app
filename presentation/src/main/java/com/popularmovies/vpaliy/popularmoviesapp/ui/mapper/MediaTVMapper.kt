package com.popularmovies.vpaliy.popularmoviesapp.ui.mapper

import android.net.Uri
import com.popularmovies.vpaliy.data.mapper.Mapper
import com.popularmovies.vpaliy.domain.entity.TVShow
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaModel
import javax.inject.Singleton

@Singleton
class MediaTVMapper:Mapper<MediaModel,TVShow>{
    override fun map(fake: TVShow):MediaModel{
        val poster=fake.poster?: Uri.parse("R.drawable.popcorn").toString()
        val backdrop=fake.backdropImage?:poster
        return MediaModel(fake.id,poster,fake.title,backdrop)
    }

    override fun reverse(real: MediaModel)=TVShow().apply {
        this.id=real.id
        this.backdropImage=real.backdrop
        this.poster=real.poster
        this.title=real.title
    }
}