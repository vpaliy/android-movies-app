package com.popularmovies.vpaliy.popularmoviesapp.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.popularmovies.vpaliy.popularmoviesapp.R;

public class RatioImageView extends AppCompatImageView {

    //16:9
    private float ratio =.5625f;

    public RatioImageView (Context context){
        this(context,null);
    }

    public RatioImageView (Context context, AttributeSet attrs){
        this(context,attrs,0);
    }

    public RatioImageView (Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
        if(attrs!=null){
            TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.RatioImageView);
            this.ratio=array.getFloat(R.styleable.RatioImageView_image_ratio,ratio);
            array.recycle();
        }
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
        requestLayout();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth=getMeasuredWidth();
        setMeasuredDimension(measuredWidth, Math.round(measuredWidth*ratio));
    }
}