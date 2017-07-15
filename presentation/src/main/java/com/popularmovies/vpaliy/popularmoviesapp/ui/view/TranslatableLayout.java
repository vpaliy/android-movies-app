package com.popularmovies.vpaliy.popularmoviesapp.ui.view;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;

public class TranslatableLayout extends ConstraintLayout {

    private static final int[] CHECKED_STATE_SET = {android.R.attr.state_checked};
    private boolean isChecked = false;
    private int staticOffset;

    public TranslatableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOffset(int offset) {
        if (offset != getTranslationY()) {
            setTranslationY(offset);
        }
    }

    public int getOffset() {
        return (int)getTranslationY();
    }

    public void setStaticOffset(int staticOffset) {
        this.staticOffset = staticOffset;
    }

    public int getStaticOffset() {
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
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }
}