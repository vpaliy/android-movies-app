package com.popularmovies.vpaliy.popularmoviesapp.ui.adapter;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.popularmovies.vpaliy.domain.model.MovieCover;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.ui.eventBus.RxBus;
import com.popularmovies.vpaliy.popularmoviesapp.ui.eventBus.events.ExposeDetailsEvent;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Constants;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.wrapper.TransitionWrapper;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import android.support.annotation.NonNull;
import butterknife.ButterKnife;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>{


    private static final String TAG=MoviesAdapter.class.getSimpleName();

    private final RxBus eventBus;
    private final LayoutInflater inflater;
    private boolean hasBeenClicked;
    private  List<MovieCover> data;

    public MoviesAdapter(@NonNull Context context, @NonNull RxBus eventBus){
        this.eventBus=eventBus;
        this.data=new ArrayList<>();
        this.inflater=LayoutInflater.from(context);

    }


    public class MovieViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        @BindView(R.id.movieImage)
        ImageView image;

        @BindView(R.id.movieTitle)
        TextView title;

        public MovieViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(!hasBeenClicked) {
                hasBeenClicked=true;
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.EXTRA_ID, data.get(getAdapterPosition()).getMovieId());
                Log.d(TAG,Boolean.toString(eventBus.hasObservers()));
                eventBus.send(new ExposeDetailsEvent(TransitionWrapper.wrap(image, bundle)));
            }
        }

        void bindData(){
            MovieCover cover=data.get(getAdapterPosition());
            Glide.with(itemView.getContext())
                    .load(cover.getPosterPath())
                    .centerCrop()
                    .priority(Priority.HIGH)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .placeholder(R.drawable.placeholder)
                    .animate(R.anim.fade_in)
                    .into(image);
            title.setText(cover.getMovieTitle());
        }
    }

    public void appendData(@NonNull List<MovieCover> movies){
        int size=getItemCount();
        data.addAll(movies);
        notifyItemRangeInserted(size,getItemCount());
    }

    public void clear(){
        data.clear();
        notifyDataSetChanged();
    }

    public void setData(@NonNull List<MovieCover> movies){
        this.data=movies;
        notifyDataSetChanged();
    }

    public void onResume(){
        hasBeenClicked=false;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root=inflater.inflate(R.layout.adapter_movie_item,parent,false);
        return new MovieViewHolder(root);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.bindData();
    }
}
