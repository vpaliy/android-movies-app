package com.popularmovies.vpaliy.popularmoviesapp.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.support.constraint.ConstraintLayout
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v4.widget.TextViewCompat
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams.*
import android.widget.HorizontalScrollView
import android.widget.TextView
import com.popularmovies.vpaliy.popularmoviesapp.R
import com.popularmovies.vpaliy.popularmoviesapp.ui.*
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.setDrawableColor

class ChipTabLayout @JvmOverloads constructor(context:Context, attrs: AttributeSet? = null, defStyle: Int = 0)
    :HorizontalScrollView(context,attrs,defStyle){

    var selected=0
    var horizontalSpacing=0
    var verticalSpacing=0
    var scaleFactor=1.1f
    var scaleDuration=200L
    var scaleDelay=0L

    private val layout =ConstraintLayout(context,attrs)

    init {
        isFillViewport=true
        layout.layoutParams= LayoutParams(MATCH_PARENT, MATCH_PARENT)
        addView(layout, layout.layoutParams)
        attrs?.let {
            val array=context.obtainStyledAttributes(it,R.styleable.ChipTabLayout)
            horizontalSpacing=array.getDimensionPixelOffset(R.styleable.ChipTabLayout_horizontal_space,horizontalSpacing)
            verticalSpacing=array.getDimensionPixelOffset(R.styleable.ChipTabLayout_vertical_space,verticalSpacing)
            scaleFactor=array.getFloat(R.styleable.ChipTabLayout_scale,scaleFactor)
            scaleDuration=array.getInteger(R.styleable.ChipTabLayout_anim_duration,200).toLong()
            array.recycle()
        }
    }

    val tabs= mutableListOf<ChipTab>()

    private var pager:ViewPager?=null

    fun setup(pager:ViewPager, adapter:ChipPagerAdapter){
        this.pager=pager
        setTabs(adapter.build())
        val callback= PagerCallback(this)
        pager.addOnPageChangeListener(callback)
        pager.addOnAdapterChangeListener(callback)
    }

    internal fun select(position:Int){
        if(tabs.size > position && (position >= 0)){
            tabs[selected].isSelected=false
            tabs[position].isSelected=true
            tabs[selected].animate()
                    .scale(1f)
                    .apply {
                        duration=scaleDuration
                        startDelay=scaleDelay
                    }.start()
            tabs[position].animate()
                    .scale(scaleFactor)
                    .apply {
                        duration=scaleDuration
                        startDelay=scaleDelay
                    }.start()
            selected=position
        }
    }

    internal fun setTabs(builders:List<ChipTab.Builder>?){
        layout.removeAllViews()
        tabs.clear()
        selected=0
        var prev:Pair<View,ConstraintLayout.LayoutParams>?=null
        builders?.forEachIndexed { index, builder ->
            val tab=builder.build(context)
            tab.id=index+1
            val params=ConstraintLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            if(prev!=null){
                prev?.let {
                    it.second.endToStart=tab.id
                    params.topToTop=it.first.id
                    params.bottomToBottom=it.first.id
                    params.startToEnd=it.first.id
                }
                if(index==builders.size-1){
                    params.endToEnd=ConstraintLayout.LayoutParams.PARENT_ID
                }
            }else{
                params.startToStart=ConstraintLayout.LayoutParams.PARENT_ID
                params.bottomToBottom=ConstraintLayout.LayoutParams.PARENT_ID
                params.topToTop=ConstraintLayout.LayoutParams.PARENT_ID
                params.horizontalChainStyle=ConstraintLayout.LayoutParams.CHAIN_SPREAD
            }
            tab.clickListener={ pager?.setCurrentItem(tabs.indexOf(tab),true) }
            tab.layoutParams=params
            tabs.add(tab)
            layout.addView(tab,params)
            prev=Pair(tab,params)
        }
        select(selected)
    }
}


@SuppressLint("ViewConstructor")
class ChipTab(context: Context, builder: Builder):AppCompatTextView(context),View.OnClickListener{

    private var selectedTextColor=builder.style.selectedTextColor
    private var selectedBackgroundColor=builder.style.selectedBackgroundColor
    private var colorBackground=builder.style.background
    private var colorText=builder.style.textColor
    private var selectedElevation=builder.style.selectedElevation

    var clickListener:((ChipTab)->Unit)?=null

    init {
        setTextColor(colorText)
        val padding=getDimensInt(R.dimen.chip_text_margin)
        setPadding(padding,0,padding,0)
        gravity=Gravity.CENTER
        setOnClickListener(this)
        text=builder.text
        background = getDrawable(R.drawable.ring)
        setDrawableColor(background,colorBackground)
        if(builder.style.textAppearance!=-1) {
            TextViewCompat.setTextAppearance(this,builder.style.textAppearance)
        }
    }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        var color=(selected then selectedTextColor)?:colorText
        setTextColor(color)
        color=(selected then selectedBackgroundColor)?:colorBackground
        setDrawableColor(background,color)
        val elevation=(selected then selectedElevation)?:0f
        setElevation(elevation)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        layoutParams.height = getDimensInt(R.dimen.chip_height)
        layoutParams.width = WRAP_CONTENT
    }

    override fun setOnClickListener(click: OnClickListener?) {
        super.setOnClickListener(this)
    }

    override fun onClick(view: View?){
        clickListener?.invoke(this)
    }

    class StyleBuilder{
        var background= Color.WHITE
        var textColor=Color.BLACK
        var selectedBackgroundColor=Color.WHITE
        var selectedTextColor=Color.BLACK
        var textAppearance=-1
        var selectedElevation=0f
    }

    data class Builder(val text:String,val style:StyleBuilder){
        fun build(context:Context)=ChipTab(context,this)
    }
}

abstract class ChipPagerAdapter(manager:FragmentManager):FragmentPagerAdapter(manager){
    internal fun build():List<ChipTab.Builder>{
        val result= mutableListOf<ChipTab.Builder>()
        (0 until count).forEach {
            val style=styleFor(it)
            val text=getPageTitle(it).toString()
            val builder= ChipTab.Builder(text,style)
            result.add(builder)
        }
        return result
    }

    abstract fun styleFor(position:Int):ChipTab.StyleBuilder
}

private class PagerCallback(val layout:ChipTabLayout):ViewPager.OnPageChangeListener,ViewPager.OnAdapterChangeListener{
    override fun onPageScrollStateChanged(state: Int) {}
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
    override fun onAdapterChanged(viewPager: ViewPager, oldAdapter: PagerAdapter?, newAdapter: PagerAdapter?) {
        newAdapter?.let {
            if(it !is ChipPagerAdapter)
                throw IllegalArgumentException("Wrong adapter! Should be ChipPagerAdapter!")
            layout.setTabs(it.build())
        }
    }

    override fun onPageSelected(position: Int) =layout.select(position)
}