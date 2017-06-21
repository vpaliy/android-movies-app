package com.popularmovies.vpaliy.popularmoviesapp.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.PaintDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Permission;
import java.util.ArrayList;
import java.util.List;

public class ChipsContainerView extends ViewGroup{

    private List<ChipView> chips;
    private int lineHeight;
    private int textColor;
    private int chipBackground;
    private int textStyle;

    public ChipsContainerView(Context context){
        this(context,null,0);
    }

    public ChipsContainerView(Context context, AttributeSet attrs){
        this(context,attrs,0);
    }

    public ChipsContainerView(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs){
        chipBackground= ContextCompat.getColor(getContext(), R.color.colorChipBackground);
        textColor=-1;
        if(attrs!=null){
            TypedArray array =getContext().obtainStyledAttributes(attrs,R.styleable.ChipsContainerView);
            textColor=array.getColor(R.styleable.ChipsContainerView_chip_text_color,textColor);
            chipBackground=array.getColor(R.styleable.ChipsContainerView_chip_background,chipBackground);
            textStyle=array.getResourceId(R.styleable.ChipsContainerView_chip_style,-1);
            array.recycle();
        }
    }

    private static class LayoutParams extends ViewGroup.LayoutParams {

        final int horizontalSpacing;
        final int verticalSpacing;

        LayoutParams(int horizontalSpacing, int verticalSpacing) {
            super(0, 0);
            this.horizontalSpacing = horizontalSpacing;
            this.verticalSpacing = verticalSpacing;
        }
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(1, 1); // default of 1px spacing
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(1, 1);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        final int width = r - l;
        int xPos = getPaddingLeft();
        int yPos = getPaddingTop();

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                final int childWidth = child.getMeasuredWidth();
                final int childHeight = child.getMeasuredHeight();
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (xPos + childWidth > width) {
                    xPos = getPaddingLeft();
                    yPos += lineHeight;
                }
                child.layout(xPos, yPos, xPos + childWidth, yPos + childHeight);
                xPos += childWidth + lp.horizontalSpacing;
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        int height = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();
        int count = getChildCount();
        int lineHeight = 0;

        int xPos = getPaddingLeft();
        int yPos = getPaddingTop();

        int childHeightMeasureSpec;
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
            childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST);
        } else {
            childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                child.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST), childHeightMeasureSpec);
                final int childWidth = child.getMeasuredWidth();
                lineHeight = Math.max(lineHeight, child.getMeasuredHeight() + lp.verticalSpacing);

                if (xPos + childWidth> width) {
                    xPos = getPaddingLeft();
                    yPos += lineHeight;
                }
                xPos += childWidth + lp.horizontalSpacing;
            }
        }
        this.lineHeight = lineHeight;
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.UNSPECIFIED) {
            height = yPos + lineHeight;

        } else if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
            if (yPos + lineHeight < height) {
                height = yPos + lineHeight;
            }
        }
        setMeasuredDimension(width, height);
    }

    public void setTags(List<String> tags){
        if(tags==null||tags.isEmpty()) return;
        if(chips==null){
            chips=new ArrayList<>(tags.size());
        }
        if(tags.size()>chips.size()){
            int diff=tags.size()-chips.size();
            for(int index=0;index<diff;index++) {
                ChipView chip = new ChipView(getContext());
                chip.setId(index+1);
                chips.add(chip);
                addView(chip,new LayoutParams(10,10));
            }
        }
        int index=0;
        for(;index<tags.size();index++){
            ChipView chip=chips.get(index);
            chip.setChipText(tags.get(index));
            if(chip.getVisibility()!= View.VISIBLE){
                chip.setVisibility(View.VISIBLE);
            }
        }

        if(index<chips.size()){
            for(;index<chips.size();index++){
                chips.get(index).setVisibility(View.GONE);
            }
        }
        requestLayout();
    }

    public class ChipView extends RelativeLayout {

        private TextView chipTextView;
        private String chipText;

        public ChipView(Context context) {
            this(context, null, 0);
        }

        public ChipView(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public ChipView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            initBackgroundColor();
            initTextView();
        }

        private void initTextView() {
            chipTextView= new TextView(getContext(),null,textStyle);

            RelativeLayout.LayoutParams chipTextParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            chipTextParams.addRule(CENTER_IN_PARENT);

            int margins = (int) getResources().getDimension(R.dimen.spacing_medium);
            int bottom=(int)getResources().getDimension(R.dimen.spacing_small);
            chipTextParams.setMargins(margins, bottom, margins,bottom);
            chipTextView.setLayoutParams(chipTextParams);
            if(textColor!=-1) chipTextView.setTextColor(textColor);
            chipTextView.setText(chipText);
            if(Permission.checkForVersion(23)){
                chipTextView.setTextAppearance(textStyle);
            }

            this.addView(chipTextView);
        }

        private void initBackgroundColor() {
            PaintDrawable bgDrawable = new PaintDrawable(chipBackground);
            bgDrawable.setCornerRadius(getResources().getDisplayMetrics().density*32f);
            setBackgroundDrawable(bgDrawable);
        }

        public void setChipText(String chipText) {
            this.chipText = chipText;
            chipTextView.setText(chipText);
        }

        public String getChipText() {
            return chipText;
        }
    }
}
