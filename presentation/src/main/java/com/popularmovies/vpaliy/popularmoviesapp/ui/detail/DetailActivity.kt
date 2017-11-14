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
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.ListWrapper
import android.view.ViewGroup
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.ImageViewTarget
import com.popularmovies.vpaliy.popularmoviesapp.App
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerMovieComponent
import com.popularmovies.vpaliy.popularmoviesapp.di.module.MovieModule
import com.popularmovies.vpaliy.popularmoviesapp.ui.detail.DetailContract.Presenter
import com.popularmovies.vpaliy.popularmoviesapp.ui.log
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.EXTRA_ID
import javax.inject.Inject
import android.content.res.ColorStateList
import android.support.v7.graphics.Palette
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.setDrawableColor


class DetailActivity:BaseActivity(),DetailContract.View{

    var presenter:Presenter?=null
        @Inject set(value) {
            field=value
            field?.let {
                it.attachView(this)
                it.attachId(intent.getStringExtra(EXTRA_ID))
            }
        }

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
        presenter?.start()
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
        applyPalette(Palette.from(bitmap).generate())
    }

    override fun showBackdrops(data: List<String>) {
        backdropAdapter.data=data.toMutableList()
    }

    override fun showCast(data: List<Role>) {
        val castAdapter=CastAdapter(this).apply {
            this.data=data.toMutableList()
        }
        adapter.add(ListWrapper(castAdapter,getString(R.string.cast_title)))
    }

    private fun applyPalette(palette: Palette) {
        var swatch: Palette.Swatch? = palette.darkVibrantSwatch
        if (swatch == null) swatch = palette.dominantSwatch
        //apply if not null
        if (swatch != null) {
            shareButton.backgroundTintList = ColorStateList.valueOf(swatch.rgb)
            descriptionRoot.setBackgroundColor(swatch.rgb)
            chips.setChipsColors(swatch.bodyTextColor,swatch.titleTextColor)
            name.setTextColor(swatch.bodyTextColor)
            release.setTextColor(swatch.bodyTextColor)
            ratings.setTextColor(swatch.bodyTextColor)
            description.setTextColor(swatch.bodyTextColor)
            duration.setTextColor(swatch.bodyTextColor)
            //money.setTextColor(swatch.bodyTextColor)
            //setDrawableColor(money, swatch.bodyTextColor)
            setDrawableColor(duration, swatch.bodyTextColor)
            setDrawableColor(release, swatch.bodyTextColor)
            setDrawableColor(ratings, swatch.bodyTextColor)
        }
    }

    override fun showMedia(movie: Movie) {
        showBackdrops(movie.backdrops!!)
        log(movie.description)
        name.text=movie.title
        release.text=movie.releaseYear
        ratings.text=movie.averageVote.toString()
        description.text=movie.description
        chips.setTags(movie.genres)
        budget.text=movie.budget
        Glide.with(this)
                .load(movie.poster)
                .asBitmap()
                .priority(Priority.IMMEDIATE)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .placeholder(R.drawable.placeholder)
                .animate(R.anim.fade_in)
                .into(object : ImageViewTarget<Bitmap>(poster) {
                    override fun setResource(resource: Bitmap) {
                        poster.setImageBitmap(resource)
                        ParamsFactory.shiftElementsFrom(poster, arrayListOf(name,release,chips,description))
                    }
                })
        adjustPlaceholder()
    }

    private fun adjustPlaceholder(){
        val layoutParams=adapter.placeholder.layoutParams
        layoutParams.height=backdropPager.height+descriptionRoot.height
        adapter.placeholder.layoutParams=layoutParams
        val offset=backdropPager.height+descriptionRoot.height+shareButton.height/2
        shareButton.staticOffset=offset
        shareButton.setOffset(offset.toFloat())
    }

    override fun showMessage(resource: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showReviews(data: List<Review>) {
        val reviewAdapter=ReviewAdapter(this).apply {
            this.data=data.toMutableList()
        }
        adapter.add(ListWrapper(reviewAdapter,getString(R.string.review_title)))
    }

    override fun showSuggested(type: Suggestion<Movie>, data: List<MediaModel>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showTrailers(data: List<Trailer>) {
        val trailerAdapter=TrailerAdapter(this).apply {
            this.data=data.toMutableList()
        }
        adapter.add(ListWrapper(trailerAdapter,getString(R.string.trailer_title)))
    }

    override fun inject() {
        DaggerMovieComponent.builder()
                .applicationComponent(App.component)
                .movieModule(MovieModule())
                .build().inject(this)
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
            poster.alpha=(alpha)
            shareButton.alpha=(1-alpha)
            max = backdropPager.height.toFloat()
            min = pageIndicator.translationY + pageIndicator.height
            //hide the indicator as well
            pageIndicator.alpha=((backdropPager.offset + backdropPager.height - min) / (max - min))
        }
    }

    private object ParamsFactory {
        internal fun shiftElementsFrom(target: View, shiftElements: List<View>) {
            var posterY = getBottomY(target) + target.height
            val posterX = target.x + target.width
            val spacing = target.resources.getDimension(R.dimen.spacing_media_details)
            val offset = target.width + spacing
            for (index in shiftElements.indices) {
                val element = shiftElements[index]
                if (posterX >= element.x && posterY + spacing >= getBottomY(element)) {
                    if (index != 0 && shouldShiftVertically(posterY, element, spacing)) {
                        posterY = shiftVertically(element, posterY)
                    } else {
                        shiftHorizontally(element, offset)
                    }
                }
            }
        }

        internal fun shouldShiftVertically(offsetY: Float, target: View, spacing: Float): Boolean {
            val targetY = getBottomY(target)
            val offsetDiff = targetY - offsetY
            return offsetDiff in 0f..spacing
        }

        internal fun shiftVertically(target: View, posterY: Float): Float {
            val spacing = target.resources.getDimension(R.dimen.spacing_media_details)
            val params = target.layoutParams as ViewGroup.MarginLayoutParams
            val offsetY = posterY - getBottomY(target) + spacing
            params.topMargin += offsetY.toInt()
            target.layoutParams=params
            return posterY-offsetY
        }

        internal fun shiftHorizontally(target: View, offset: Float) {
            val params = target.layoutParams as ViewGroup.MarginLayoutParams
            params.leftMargin += offset.toInt()
            target.layoutParams=params
        }

        internal fun getBottomY(view: View): Float {
            val screenLocation = IntArray(2)
            view.getLocationOnScreen(screenLocation)
            return screenLocation[1].toFloat()
        }
    }
}