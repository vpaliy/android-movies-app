package com.popularmovies.vpaliy.popularmoviesapp.ui.view

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet

import com.popularmovies.vpaliy.popularmoviesapp.R

class RatioImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0):
        AppCompatImageView(context, attrs, defStyle) {

    //16:9
    private var ratio = .5625f

    init {
        if (attrs != null) {
            val array = context.obtainStyledAttributes(attrs, R.styleable.RatioImageView)
            this.ratio = array.getFloat(R.styleable.RatioImageView_image_ratio, ratio)
            array.recycle()
        }
    }

    fun setRatio(ratio: Float) {
        this.ratio = ratio
        requestLayout()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val measuredWidth = measuredWidth
        setMeasuredDimension(measuredWidth, Math.round(measuredWidth * ratio))
    }
}