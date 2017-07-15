package com.popularmovies.vpaliy.popularmoviesapp.ui.view;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.popularmovies.vpaliy.popularmoviesapp.R;

public class ParallaxImageView extends RoundedImageView {

    private static final int[] STATE_PINNED = { R.attr.state_pinned };
    private boolean isChecked = false;
    private float minOffset;
    private float staticOffset;
    private boolean isPinned = false;
    private boolean immediatePin = false;

    public ParallaxImageView(Context context){
        this(context,null);
    }

    public ParallaxImageView(Context context, AttributeSet attrs){
        this(context,attrs,0);
    }

    public ParallaxImageView(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
    }

    public void setOffset(float offset) {
        if (offset != getTranslationY()) {
            offset = Math.max(minOffset, offset);
            setTranslationY(offset);
        }
    }

    public int getOffset() {
        return (int)getTranslationY();
    }

    public void setStaticOffset(float staticOffset) {
        this.staticOffset = staticOffset;
    }

    public void setMinOffset(float minOffset) {
        this.minOffset = minOffset;
    }

    public float getStaticOffset() {
        return staticOffset;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        if (this.isChecked != isChecked) {
            this.isChecked = isChecked;
            refreshDrawableState();
        }
    }


    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isPinned) {
            mergeDrawableStates(drawableState, STATE_PINNED);
        }
        return drawableState;
    }

    public boolean isPinned() {
        return isPinned;
    }

    public void setPinned(boolean isPinned) {
        if (this.isPinned != isPinned) {
            this.isPinned = isPinned;
            refreshDrawableState();
            if (isPinned && immediatePin) {
                jumpDrawablesToCurrentState();
            }
        }
    }

    public boolean isImmediatePin() {
        return immediatePin;
    }

    public void setImmediatePin(boolean immediatePin) {
        this.immediatePin = immediatePin;
    }
}
