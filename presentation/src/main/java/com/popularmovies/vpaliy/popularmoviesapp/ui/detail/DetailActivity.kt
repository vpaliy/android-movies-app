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
import com.popularmovies.vpaliy.popularmoviesapp.ui.getMinHeight
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaModel
import com.rd.animation.type.AnimationType
import kotlinx.android.synthetic.main.activity_media_details.*
import kotlinx.android.synthetic.main.layout_media_description.*
import android.support.v7.widget.RecyclerView

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
        details.adapter=adapter
        backdropPager.adapter=backdropAdapter
        details.onFlingListener=flingListener
        details.addOnScrollListener(listener)
    }

    private fun onLoaded(image:ImageView, bitmap: Bitmap){
        pageIndicator.setAnimationType(AnimationType.THIN_WORM)
        pageIndicator.translationY=image.height-pageIndicator.height*2.5f
        descriptionRoot.staticOffset=image.height.toFloat()
        descriptionRoot.offset=image.height.toFloat()
        val posterOffset=image.height-poster.height*0.33f
        poster.minOffset=posterOffset
        poster.staticOffset=posterOffset
        poster.setOffset(posterOffset)
        poster.isPinned=true
        shareButton.setMinOffset(backdropPager.getMinHeight()-shareButton.height/2f)
    }

    override fun showBackdrops(data: List<String>) {
        backdropAdapter.data=data.toMutableList()
    }

    override fun showCast(data: List<Role>) {

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
        chips.setTags(data)
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showTrailers(data: List<Trailer>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun inject() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val flingListener = object : RecyclerView.OnFlingListener() {
        override fun onFling(velocityX: Int, velocityY: Int): Boolean {
            poster.isImmediatePin=true
            backdropPager.isImmediatePin=true
            return false
        }
    }

    private val listener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            poster.isImmediatePin=(newState == RecyclerView.SCROLL_STATE_SETTLING)
            backdropPager.isImmediatePin=(newState == RecyclerView.SCROLL_STATE_SETTLING)
        }

        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val scrollY = adapter.placeholder.top
            backdropPager.offset=scrollY
            poster.setOffset(poster.staticOffset + scrollY)
            descriptionRoot.offset=(descriptionRoot.staticOffset + scrollY)
            shareButton.staticOffset=(shareButton.staticOffset + scrollY)

            var min = poster.translationY + poster.height
            var max = descriptionRoot.staticOffset + descriptionRoot.height
            //hide the poster as it goes up
            val alpha = (descriptionRoot.offset + descriptionRoot.height - min) / (max - min)
            poster.alpha=(1-alpha)
            shareButton.alpha=(1f - alpha)
            max = backdropPager.height.toFloat()
            min = pageIndicator.translationY + pageIndicator.height
            //hide the indicator as well
            pageIndicator.alpha=((backdropPager.offset + backdropPager.height - min) / (max - min))
        }
    }
}