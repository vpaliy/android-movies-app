package com.popularmovies.vpaliy.popularmoviesapp.ui.adapter;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import android.support.annotation.NonNull;

public class MovieBackdropsAdapter extends PagerAdapter{

    private List<String> movieBackdrops;

    private static final int NUMBER_OF_BACKDROPS=7;

    public MovieBackdropsAdapter(){
        this.movieBackdrops=new ArrayList<>();
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        ImageView image=new ImageView(container.getContext());
        image.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        Glide.with(container.getContext())
                .load(movieBackdrops.get(position))
                .asBitmap()
                .centerCrop()
                .skipMemoryCache(true)
                .into(image);
        container.addView(image);
        return image;
    }

    @Override
    public int getCount() {
        return movieBackdrops.size()>=NUMBER_OF_BACKDROPS
                ?NUMBER_OF_BACKDROPS:movieBackdrops.size();
    }

    public void setData(@NonNull List<String> backdrops){
        this.movieBackdrops=backdrops;
        notifyDataSetChanged();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view=View.class.cast(object);
        container.removeView(view);
    }
}
