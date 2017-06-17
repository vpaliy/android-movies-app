package com.popularmovies.vpaliy.popularmoviesapp.ui.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Locale;

public class Ratings extends RelativeLayout {

    private RatingsView ratingsView;
    private TextView ratingsText;

    public Ratings(Context context) {
        super(context);
        init();
    }

    public Ratings(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Ratings(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        ratingsView=new RatingsView(getContext());
        getLayoutParams().width= LayoutParams.WRAP_CONTENT;
        getLayoutParams().height= LayoutParams.WRAP_CONTENT;

        addView(ratingsView);
        RelativeLayout.LayoutParams params=new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        ratingsText=new TextView(getContext());
        addView(ratingsText,params);
        ratingsView.setListener(position -> ratingsText.setText(String.format(Locale.US,"%.1f",position)));
    }

    
}