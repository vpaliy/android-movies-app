package com.popularmovies.vpaliy.popularmoviesapp.ui.detail

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import com.popularmovies.vpaliy.domain.interactor.params.Suggestion
import com.popularmovies.vpaliy.popularmoviesapp.R
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseActivity
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaModel
import com.rd.animation.type.AnimationType
import kotlinx.android.synthetic.main.activity_media_details.*
import kotlinx.android.synthetic.main.layout_media_description.*
import android.support.v7.widget.RecyclerView
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.ListWrapper
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.popularmovies.vpaliy.popularmoviesapp.App
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerMovieComponent
import com.popularmovies.vpaliy.popularmoviesapp.di.module.MovieModule
import com.popularmovies.vpaliy.popularmoviesapp.ui.detail.DetailContract.Presenter
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.EXTRA_ID
import javax.inject.Inject
import android.content.res.ColorStateList
import android.support.constraint.ConstraintLayout
import android.support.v7.graphics.Palette
import android.view.View
import com.bumptech.glide.request.target.ImageViewTarget
import com.popularmovies.vpaliy.domain.entity.*
import com.popularmovies.vpaliy.popularmoviesapp.ui.*
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseAdapter
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.OnReachBottomListener
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.setDrawableColor
import com.vpaliy.kotlin_extensions.*

class DetailActivity:BaseActivity(),DetailContract.View{

    var presenter:Presenter?=null
        @Inject set(value) {
            field=value
            field?.let {
                it.attachView(this)
                it.attachId(intent.getStringExtra(EXTRA_ID))
            }
        }

    private val hidden by lazy(LazyThreadSafetyMode.NONE){
        listOf(shareButton,poster,pageIndicator,descriptionRoot,poster,details)
    }

    private val adapter by lazy(LazyThreadSafetyMode.NONE){
        DetailAdapter(this)
    }

    private val backdropAdapter by lazy(LazyThreadSafetyMode.NONE){
        BackdropAdapter(this,this::onLoaded)
    }

    private val suggestionMap= mutableMapOf<SimilarityType,BaseAdapter<MediaModel>>()

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

        hidden.forEach(View::show)
    }

    override fun showBackdrops(data: List<String>) {
        backdropAdapter.data=data.toMutableList()
    }

    override fun showCast(data: List<Role>) {
        val castAdapter=CastAdapter(this).apply {
            this.data=data.toMutableList()
        }
        adapter.addFirst(ListWrapper(castAdapter,getString(R.string.cast_title)))
    }

    private fun applyPalette(palette: Palette) {
        var swatch: Palette.Swatch? = palette.darkVibrantSwatch
        if (swatch == null) swatch = palette.dominantSwatch
        //apply if not null
        if (swatch != null) {
            shareButton.backgroundTintList = ColorStateList.valueOf(swatch.rgb)
            descriptionRoot.setBackgroundColor(swatch.rgb)
            chips.setChipsColors(swatch.bodyTextColor,swatch.titleTextColor)
            movieTitle.setTextColor(swatch.bodyTextColor)
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
        movieTitle.text=movie.title
        release.text=movie.releaseYear
        ratings.text=movie.averageVote.toString()
        description.text=movie.description
        chips.setTags(movie.genres)
        //budget.text=movie.budget
        Glide.with(this)
                .load(movie.poster)
                .asBitmap()
                .priority(Priority.IMMEDIATE)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .placeholder(R.drawable.placeholder)
                .animate(R.anim.fade_in)
                .into(poster)
        description.afterPost {
            adjustDescription()
            post(this@DetailActivity::adjustPlaceholder)
        }
    }

    private fun adjustPlaceholder(){
        val layoutParams=adapter.placeholder.layoutParams
        layoutParams.height=backdropPager.height+descriptionRoot.height
        adapter.placeholder.layoutParams=layoutParams
        val offset=backdropPager.height+descriptionRoot.height-shareButton.height/2
        shareButton.staticOffset=offset
        shareButton.setOffset(offset.toFloat())
    }

    //TODO find a generic way to accomplish this
    private fun adjustDescription(){
        if((description.endY() > dummy.endY())){
            if((dummy.endY() - duration.endY()) < 0){
                var params=release.layoutParams as ConstraintLayout.LayoutParams
                params.startToStart=ConstraintLayout.LayoutParams.PARENT_ID
                params.leftToRight=ConstraintLayout.LayoutParams.UNSET
                params.topToBottom=dummy.id
                //  params.topMargin=getDimen(R.dimen.spacing_medium).toInt()
                release.layoutParams=params

                params=duration.layoutParams as ConstraintLayout.LayoutParams
                params.startToStart=movieTitle.id
                params.topToBottom=movieTitle.id
                //  params.topMargin=getDimen(R.dimen.spacing_medium).toInt()
                duration.layoutParams=params

                params=chips.layoutParams as ConstraintLayout.LayoutParams
                params.startToStart=ConstraintLayout.LayoutParams.PARENT_ID
                params.leftToRight=ConstraintLayout.LayoutParams.UNSET
                params.topToBottom=release.id
                //  params.topMargin=getDimen(R.dimen.spacing_medium).toInt()
                chips.layoutParams=params

                params=description.layoutParams as ConstraintLayout.LayoutParams
                params.startToStart=ConstraintLayout.LayoutParams.PARENT_ID
                params.leftToRight=ConstraintLayout.LayoutParams.UNSET
                params.topToBottom=chips.id
                params.topMargin=getDimensionPixelOffset(R.dimen.spacing_medium)
                description.layoutParams=params
            }else if((dummy.endY()-duration.endY()) <= (2* duration.height)){
                var params=chips.layoutParams as ConstraintLayout.LayoutParams
                params.startToStart=ConstraintLayout.LayoutParams.PARENT_ID
                params.leftToRight=ConstraintLayout.LayoutParams.UNSET
                params.topToBottom=dummy.id
                //  params.topMargin=getDimen(R.dimen.spacing_medium).toInt()
                chips.layoutParams=params

                params=description.layoutParams as ConstraintLayout.LayoutParams
                params.startToStart=ConstraintLayout.LayoutParams.PARENT_ID
                params.leftToRight=ConstraintLayout.LayoutParams.UNSET
                params.topToBottom=chips.id
                params.topMargin=getDimensionPixelOffset(R.dimen.spacing_medium)
                description.layoutParams=params
            }
        }
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

    override fun appendSuggested(type: SimilarityType, data: List<MediaModel>) {
        suggestionMap[type]?.append(data.toMutableList())
    }

    override fun showSuggested(type: SimilarityType, data: List<MediaModel>) {
        val suggestedAdapter=SuggestedAdapter(this)
        suggestedAdapter.data=data.toMutableList()
        suggestionMap.put(type,suggestedAdapter)
        val title=(type == SimilarityType.SIMILAR) then getString(R.string.media_similar_content)
                ?:getString(R.string.media_recommendations)
        val wrapper=ListWrapper(suggestedAdapter,title, { presenter?.more(type) })
        adapter.add(wrapper)
    }

    override fun showTrailers(data: List<Trailer>) {
        val trailerAdapter=TrailerAdapter(this).apply {
            this.data=data.toMutableList()
        }
        adapter.addLast(ListWrapper(trailerAdapter,getString(R.string.trailer_title)))
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

        private val posterHeight by lazy(LazyThreadSafetyMode.NONE) {
            getDimension(R.dimen.details_poster_height)
        }

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
            shareButton.setOffset(shareButton.staticOffset+scrollY.toFloat())

            var min = poster.translationY + poster.height
            var max = descriptionRoot.staticOffset + descriptionRoot.height
            //hide the poster as it goes up
            val alpha = (descriptionRoot.offset + descriptionRoot.height - min) / (max - min)
            val params=poster.layoutParams
            //TODO make the image disappear on scroll
            // params.height=(posterHeight * alpha).toInt()
            poster.layoutParams=params
            poster.alpha=(alpha)
            shareButton.alpha=(1-alpha)
            max = backdropPager.height.toFloat()
            min = pageIndicator.translationY + pageIndicator.height
            //hide the indicator as well
            pageIndicator.alpha=((backdropPager.offset + backdropPager.height - min) / (max - min))
        }
    }
}