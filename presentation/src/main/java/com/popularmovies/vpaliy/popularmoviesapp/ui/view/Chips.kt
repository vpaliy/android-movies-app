package com.popularmovies.vpaliy.popularmoviesapp.ui.view

import android.content.Context
import android.graphics.PorterDuff
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.popularmovies.vpaliy.popularmoviesapp.R
import java.util.Arrays

class Chips @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0)
  : ViewGroup(context, attrs, defStyle) {

  private var chips = mutableListOf<TextView>()
  private var lineHeight: Int = 0
  var horizontalSpacing: Int = 0
  var verticalSpacing: Int = 0
  private var textAppearance: Int = 0
  private var chipBackground: Int = R.drawable.ring

  init {
    attrs?.let {
      val array = context.obtainStyledAttributes(it, R.styleable.Chips)
      horizontalSpacing = array.getDimension(R.styleable.Chips_horizontal_spacing, 1f).toInt()
      verticalSpacing = array.getDimension(R.styleable.Chips_vertical_spacing, 1f).toInt()
      chipBackground = array.getResourceId(R.styleable.Chips_chip_background, R.drawable.ring)
      textAppearance = array.getResourceId(R.styleable.Chips_text_style, -1)
      val arrayRes = array.getResourceId(R.styleable.Chips_array, -1)
      if (arrayRes != -1) {
        val textArray = resources.getStringArray(arrayRes)
        setTags(Arrays.asList(*textArray))
      }
      array.recycle()
    }
  }

  private class LayoutParams internal constructor(internal val horizontalSpacing: Int,
                                                  internal val verticalSpacing: Int)
    : ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
      ViewGroup.LayoutParams.WRAP_CONTENT)

  override fun generateDefaultLayoutParams(): ViewGroup.LayoutParams {
    return Chips.LayoutParams(horizontalSpacing, verticalSpacing)
  }

  override fun generateLayoutParams(p: ViewGroup.LayoutParams): ViewGroup.LayoutParams {
    return Chips.LayoutParams(horizontalSpacing, verticalSpacing)
  }

  override fun checkLayoutParams(p: ViewGroup.LayoutParams): Boolean {
    return p is Chips.LayoutParams
  }


  override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
    val count = childCount
    val width = r - l
    var xPos = paddingLeft
    var yPos = paddingTop

    for (i in 0..count - 1) {
      val child = getChildAt(i)
      if (child.visibility != View.GONE) {
        val childWidth = child.measuredWidth
        val childHeight = child.measuredHeight
        val lp = child.layoutParams as Chips.LayoutParams
        if (xPos + childWidth > width) {
          xPos = paddingLeft
          yPos += lineHeight
        }
        child.layout(xPos, yPos, xPos + childWidth, yPos + childHeight)
        xPos += childWidth + lp.horizontalSpacing
      }
    }
  }

  fun getChips(): List<TextView>? {
    return chips
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    val width = View.MeasureSpec.getSize(widthMeasureSpec) - paddingLeft - paddingRight
    var height = View.MeasureSpec.getSize(heightMeasureSpec) - paddingTop - paddingBottom
    val count = childCount
    var lineHeight = 0

    var xPos = paddingLeft
    var yPos = paddingTop

    val childHeightMeasureSpec: Int
    if (View.MeasureSpec.getMode(heightMeasureSpec) == View.MeasureSpec.AT_MOST) {
      childHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.AT_MOST)
    } else {
      childHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    }

    for (i in 0..count - 1) {
      val child = getChildAt(i)
      if (child.visibility != View.GONE) {
        val lp = child.layoutParams as Chips.LayoutParams
        child.measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.AT_MOST), childHeightMeasureSpec)
        val childWidth = child.measuredWidth
        lineHeight = Math.max(lineHeight, child.measuredHeight + lp.verticalSpacing)

        if (xPos + childWidth > width) {
          xPos = paddingLeft
          yPos += lineHeight
        }
        xPos += childWidth + lp.horizontalSpacing
      }
    }
    this.lineHeight = lineHeight
    if (View.MeasureSpec.getMode(heightMeasureSpec) == View.MeasureSpec.UNSPECIFIED) {
      height = yPos + lineHeight

    } else if (View.MeasureSpec.getMode(heightMeasureSpec) == View.MeasureSpec.AT_MOST) {
      if (yPos + lineHeight < height) {
        height = yPos + lineHeight
      }
    }
    setMeasuredDimension(width, height)
  }

  fun setTags(tags: List<String>?) {
    if (tags == null || tags.isEmpty()) return
    if (tags.size > chips.size) {
      val diff = tags.size - chips.size
      for (index in 0..diff - 1) {
        val chip = TextView(context)
        chip.background = ContextCompat.getDrawable(context, chipBackground)
        if (textAppearance != -1) {
          chip.setTextAppearance(context, textAppearance)
        }
        chips.add(chip)
        addView(chip)
      }
    }
    var index = 0
    while (index < tags.size) {
      val chip = chips[index]
      chip.text = tags[index]
      if (chip.visibility != View.VISIBLE) {
        chip.visibility = View.VISIBLE
      }
      index++
    }

    if (index < chips.size) {
      while (index < chips.size) {
        chips[index].visibility = View.GONE
        index++
      }
    }
    requestLayout()
  }

  fun setChipsColors(textColor: Int, backgroundTextColor: Int) {
    chips.forEach {
      it.setTextColor(textColor)
      it.background.setColorFilter(backgroundTextColor,
          PorterDuff.Mode.MULTIPLY)
    }
  }
}
