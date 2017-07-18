package com.popularmovies.vpaliy.popularmoviesapp.ui.season;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;

public class ImagesAdapter  extends PagerAdapter {

    private List<String> backdrops;
    private LayoutInflater inflater;

    public ImagesAdapter(Context context){
        this.backdrops =new ArrayList<>();
        this.inflater=LayoutInflater.from(context);
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        View view=inflater.inflate(R.layout.adapter_backdrop_item,container,false);
        ImageView image= ButterKnife.findById(view,R.id.backdropImage);
        ProgressBar progressBar=ButterKnife.findById(view,R.id.progressBar);

        Glide.with(container.getContext())
                .load(backdrops.get(position))
                .asBitmap()
                .priority(Priority.IMMEDIATE)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(new ImageViewTarget<Bitmap>(image) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        image.setImageBitmap(resource);
                        progressBar.setVisibility(View.GONE);
                    }
                });
        container.addView(view);
        return view;
    }

    public void appendData(List<String> data){
        this.backdrops =data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(backdrops.size()>=5) return 5;
        return backdrops.size();
    }

    public void setData(@NonNull List<String> backdrops){
        this.backdrops =backdrops;
        notifyDataSetChanged();
    }

    public void setPoster(String poster){
        if(poster!=null){
            backdrops = Collections.singletonList(poster);
            notifyDataSetChanged();
        }
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