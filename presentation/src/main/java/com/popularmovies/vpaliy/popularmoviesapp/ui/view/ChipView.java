package com.popularmovies.vpaliy.popularmoviesapp.ui.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.PaintDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Permission;

public class ChipView extends RelativeLayout{

    private TextView chipTextView;
    private int textColor;
    private String chipText;
    private int chipBackground;
    private int textStyle;

    public ChipView(Context context) {
        this(context, null, 0);
    }

    public ChipView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChipView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        initBackgroundColor();
        initTextView();
    }

    private void initAttrs(AttributeSet attrs){
        this.chipBackground=ContextCompat.getColor(getContext(),R.color.colorChipBackground);
        this.textColor=-1;
        if(attrs!=null){
            TypedArray array =getContext().obtainStyledAttributes(attrs,R.styleable.ChipView);
            this.textColor=array.getColor(R.styleable.ChipView_chip_text_color,textColor);
            this.chipText=array.getString(R.styleable.ChipView_chip_text);
            this.chipBackground=array.getColor(R.styleable.ChipView_chip_background,chipBackground);
            this.textStyle=array.getResourceId(R.styleable.ChipView_chip_style,-1);
            array.recycle();
        }
    }

    private void initTextView() {
        chipTextView= new TextView(getContext(),null,textStyle);

        LayoutParams chipTextParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        chipTextParams.addRule(CENTER_IN_PARENT);

        int margins = (int) getResources().getDimension(R.dimen.spacing_medium);
        int bottom=(int)getResources().getDimension(R.dimen.spacing_small);
        chipTextParams.setMargins(margins, bottom, margins,bottom);
        chipTextView.setLayoutParams(chipTextParams);
        if(textColor!=-1) chipTextView.setTextColor(textColor);
        chipTextView.setText(chipText);

        this.addView(chipTextView);
    }

    private void initBackgroundColor() {
        PaintDrawable bgDrawable = new PaintDrawable(chipBackground);
        bgDrawable.setCornerRadius(getResources().getDisplayMetrics().density*32f);
        setBackgroundDrawable(bgDrawable);
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        chipTextView.setTextColor(textColor);
    }

    public void setChipText(String chipText) {
        this.chipText = chipText;
        chipTextView.setText(chipText);
    }

    public void setTextStyle(int textStyle) {
        this.textStyle = textStyle;
        if(Permission.checkForVersion(23)){
            chipTextView.setTextAppearance(textStyle);
        }
    }
}