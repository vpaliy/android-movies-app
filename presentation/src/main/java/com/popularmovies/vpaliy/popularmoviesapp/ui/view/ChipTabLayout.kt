package com.popularmovies.vpaliy.popularmoviesapp.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.TextView
import com.popularmovies.vpaliy.popularmoviesapp.R
import com.popularmovies.vpaliy.popularmoviesapp.ui.getDimensInt
import com.popularmovies.vpaliy.popularmoviesapp.ui.then

class ChipTabLayout @JvmOverloads constructor(context:Context, attrs: AttributeSet? = null, defStyle: Int = 0)
    :HorizontalScrollView(context,attrs,defStyle){

    val layout=StripLayout(context)


}


@SuppressLint("ViewConstructor")
class ChipTab(context: Context, builder: ChipBuilder):TextView(context),View.OnClickListener{

    private var selectedTextColor=builder.selectedTextColor
    private var selectedBackgroundColor=builder.selectedBackgroundColor
    private var colorBackground=builder.background
    private var colorText=builder.textColor
    var clickListener:((ChipTab)->Unit)?=null

    init {
        setBackgroundColor(builder.background)
        setTextColor(builder.textColor)
        val padding=getDimensInt(R.dimen.chip_text_margin)
        setPadding(padding,0,padding,0)
        gravity=Gravity.CENTER
        setOnClickListener(this)
        text=builder.text

    }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        var color=(selected then selectedTextColor)?:colorText
        setTextColor(color)
        color=(selected then selectedBackgroundColor)?:colorBackground
        setBackgroundColor(color)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        layoutParams.height = getDimensInt(R.dimen.chip_height)
        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
    }

    override fun onClick(view: View?){
        clickListener?.invoke(this)
    }
}

class StripLayout(context: Context):LinearLayout(context){

    val tabs= mutableListOf<ChipTab>()

    var selected=-1
    var horizontalSpacing=0

    init { orientation= HORIZONTAL }
}

class ChipBuilder(val text:String){
    var background= Color.WHITE
    var textColor=Color.BLACK
    var selectedBackgroundColor=Color.WHITE
    var selectedTextColor=Color.BLACK
}