package com.popularmovies.vpaliy.popularmoviesapp.ui.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.popularmovies.vpaliy.popularmoviesapp.R;

import java.util.Locale;

public class Ratings extends RelativeLayout {

    private RatingsView ratingsView;
    private TextView ratingsText;

    private int textStyle;
    private float textSize;
    private int textColor;

    public Ratings(Context context) {
        this(context,null,-1);
    }

    public Ratings(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public Ratings(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAttrs(attrs);
        init(attrs);
    }

    private void initAttrs(AttributeSet attrs){
        if(attrs!=null) {
            final TypedArray array = getContext().obtainStyledAttributes(attrs,
                    R.styleable.Ratings);
            final int N = array.getIndexCount();
            for (int i = 0; i < N; ++i) {
                int attr = array.getIndex(i);
                if(attr==R.styleable.Ratings_ratings_text_color) {
                    this.textColor=array.getColor(R.styleable.Ratings_ratings_text_color,-1);
                }else if(attr==R.styleable.Ratings_ratings_text_style){
                    this.textStyle=array.getResourceId(R.styleable.Ratings_ratings_text_style,R.style.Widget_Title);
                }else if(attr==R.styleable.Ratings_ratings_text_size) {
                    this.textSize = array.getDimension(R.styleable.Ratings_ratings_text_size, -1);
                }
            }
            array.recycle();
        }
    }

    private void init(AttributeSet attrs) {
        ratingsView=new RatingsView(getContext(),attrs);
        addView(ratingsView,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        RelativeLayout.LayoutParams params=new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        ratingsText=new TextView(getContext());
        ratingsText.setLayoutParams(params);
        if(textSize>0) ratingsText.setTextSize(textSize);
        if(textColor>0) ratingsText.setTextColor(textColor);
        addView(ratingsText,params);
        ratingsView.setListener(position -> ratingsText.setText(String.format(Locale.US,"%.1f",position)));
    }

}