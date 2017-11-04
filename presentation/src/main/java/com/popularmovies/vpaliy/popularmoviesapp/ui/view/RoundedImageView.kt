package com.popularmovies.vpaliy.popularmoviesapp.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet

import com.popularmovies.vpaliy.popularmoviesapp.R

open class RoundedImageView @JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0)
    :AppCompatImageView(context, attrs, defStyle) {

    private var radius = 18.0f
    private var path: Path? = null
    private var rect: RectF? = null

    init {
        if (attrs != null) {
            val array = getContext().obtainStyledAttributes(attrs,R.styleable.RoundedImageView)
            val N = array.indexCount
            for (i in 0..N - 1) {
                val attr = array.getIndex(i)
                if (attr == R.styleable.RoundedImageView_radius) {
                    radius = array.getFloat(R.styleable.RoundedImageView_radius, 18f)
                }
            }
            array.recycle()
        }
        init()
    }

    private fun init() {
        path = Path()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        if (w != oldw || h != oldh) {
            rect = RectF(0f, 0f, this.width.toFloat(), this.height.toFloat())
        }
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas) {
        path!!.addRoundRect(rect, radius, radius, Path.Direction.CW)
        canvas.clipPath(path!!)
        super.onDraw(canvas)
    }
}
