package com.popularmovies.vpaliy.popularmoviesapp.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

import com.popularmovies.vpaliy.popularmoviesapp.R;

public class AspectRatioCardView extends CardView {
    private float mRatio = 1.0f;
    public AspectRatioCardView(Context context) {
        super(context);
    }

    public AspectRatioCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        extractAttrs(context,attrs);
    }

    public AspectRatioCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        extractAttrs(context,attrs);
    }

    private void extractAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.AspectRatioCardView,
                0, 0);

        try {
            mRatio = a.getFloat(R.styleable.AspectRatioCardView_ratio, 1.0f);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        int widthWithoutPadding = width - getPaddingLeft() - getPaddingRight();
        int heightWithoutPadding = height - getPaddingTop() - getPaddingBottom();

        int maxWidth = (int) (heightWithoutPadding / mRatio);
        int maxHeight = (int) (widthWithoutPadding * mRatio);

        if (widthWithoutPadding  > maxWidth) {
            width = maxWidth + getPaddingLeft() + getPaddingRight();
        } else {
            height = maxHeight + getPaddingTop() + getPaddingBottom();
        }

        super.onMeasure(
                MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        );
    }
}