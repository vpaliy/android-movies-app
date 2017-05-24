package com.popularmovies.vpaliy.popularmoviesapp.ui.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.ui.eventBus.RxBus;
import java.util.ArrayList;
import java.util.List;
import butterknife.ButterKnife;

import android.support.annotation.NonNull;
import butterknife.BindView;

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.MediaViewHolder>{

    private List<MediaCover> data;
    private LayoutInflater inflater;
    private RxBus rxBus;
    private boolean hasBeenClicked;

    public MediaAdapter(@NonNull Context context,
                        @NonNull RxBus rxBus){
        this.inflater=LayoutInflater.from(context);
        this.rxBus=rxBus;
        this.data=new ArrayList<>();
    }

    public void setData(List<MediaCover> data) {
        this.data = data;
    }

    public class MediaViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.media_release_year)
        TextView releaseYear;

        @BindView(R.id.media_poster)
        ImageView posterImage;

        @BindView(R.id.media_rating)
        TextView ratings;

        @BindView(R.id.media_title)
        TextView mediaTitle;

        public MediaViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        void onBindData(){
            MediaCover cover=at(getAdapterPosition());
            releaseYear.setText(convertToYear(cover.getReleaseDate()));
            String bullet="\u25CF"+" ";
            String titleText=bullet+cover.getMovieTitle();
            mediaTitle.setText(titleText);
            ratings.setText(Double.toString(cover.getAverageRate()));
            Glide.with(itemView.getContext())
                    .load(cover.getPosterPath())
                    .priority(Priority.HIGH)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .placeholder(R.drawable.placeholder)
                    .animate(R.anim.fade_in)
                    .into(posterImage);
        }

        private String convertToYear(String date){
            return date.substring(0,4);
        }
    }

    private MediaCover at(int index){
        return data.get(index);
    }

    public void appendData(@NonNull List<MediaCover> movies){
        int size=getItemCount();
        data.addAll(movies);
        notifyItemRangeInserted(size,getItemCount());
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public MediaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root=inflater.inflate(R.layout.adapter_media_item,parent,false);
        return new MediaViewHolder(root);
    }

    @Override
    public void onBindViewHolder(MediaViewHolder holder, int position) {
        holder.onBindData();
    }
}
