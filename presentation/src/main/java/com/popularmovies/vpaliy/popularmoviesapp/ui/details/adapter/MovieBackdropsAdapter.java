package com.popularmovies.vpaliy.popularmoviesapp.ui.details.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.popularmovies.vpaliy.popularmoviesapp.R;

import java.util.ArrayList;
import java.util.List;
import android.support.annotation.NonNull;
import android.widget.ProgressBar;

import butterknife.ButterKnife;

public class MovieBackdropsAdapter extends PagerAdapter{

    private List<String> movieBackdrops;
    private LayoutInflater inflater;
    private static final int NUMBER_OF_BACKDROPS=5;

    public MovieBackdropsAdapter(Context context){
        this.movieBackdrops=new ArrayList<>();
        this.inflater=LayoutInflater.from(context);
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        View view=inflater.inflate(R.layout.adapter_backdrop_item,container,false);
        ImageView image= ButterKnife.findById(view,R.id.backdropImage);
        ProgressBar progressBar=ButterKnife.findById(view,R.id.progressBar);

        Glide.with(container.getContext())
                .load(movieBackdrops.get(position))
                .asBitmap()
                .centerCrop()
                .into(new ImageViewTarget<Bitmap>(image) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        image.setImageBitmap(resource);
                        progressBar.setVisibility(View.INVISIBLE);

                    }
                });
        container.addView(view);
        return view;
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
