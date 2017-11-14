package com.popularmovies.vpaliy.popularmoviesapp.ui.detail

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import com.popularmovies.vpaliy.domain.entity.Movie
import com.popularmovies.vpaliy.domain.entity.Review
import com.popularmovies.vpaliy.domain.entity.Role
import com.popularmovies.vpaliy.domain.entity.Trailer
import com.popularmovies.vpaliy.domain.interactor.params.Suggestion
import com.popularmovies.vpaliy.popularmoviesapp.R
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseActivity
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaModel
import com.rd.animation.type.AnimationType
import kotlinx.android.synthetic.main.activity_media_details.*
import kotlinx.android.synthetic.main.layout_media_description.*

class DetailActivity:BaseActivity(),DetailContract.View{

    private val adapter by lazy {
        DetailAdapter(this)
    }

    private val backdropAdapter by lazy {
        BackdropAdapter(this,this::onLoaded)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media_details)

    }

    private fun onLoaded(image:ImageView, bitmap: Bitmap){
        pageIndicator.setAnimationType(AnimationType.THIN_WORM)
        pageIndicator.translationY=image.height-pageIndicator.height*2.5f
        descriptionRoot.staticOffset=image.height.toFloat()
        descriptionRoot.offset=image.height.toFloat()
        val posterOffset=image.height-poster.height*0.33f
        poster.setMinOffset(posterOffset)

    }

    override fun showBackdrops(data: List<String>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showCast(data: List<Role>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMedia(media: MediaModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMessage(resource: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showReviews(data: List<Review>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showSuggested(type: Suggestion<Movie>, data: List<MediaModel>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showTags(data: List<String>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showTrailers(data: List<Trailer>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun inject() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}