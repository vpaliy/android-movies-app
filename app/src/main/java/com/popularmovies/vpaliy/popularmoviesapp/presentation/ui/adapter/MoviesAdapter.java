package com.popularmovies.vpaliy.popularmoviesapp.presentation.ui.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.domain.model.Movie;
import com.popularmovies.vpaliy.popularmoviesapp.presentation.ui.view.SquareImage;
import com.squareup.otto.Bus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>{

    private final Bus eventBus;
    private final List<Movie> data;
    private final LayoutInflater inflater;

    public MoviesAdapter(@NonNull Context context,@NonNull List<Movie> data, @NonNull Bus eventBus){
        this.eventBus=eventBus;
        this.data=data;
        this.inflater=LayoutInflater.from(context);

    }


    public class MovieViewHolder extends RecyclerView.ViewHolder
                implements View.OnClickListener{

        @BindView(R.id.movieImage)
        protected SquareImage image;

        public MovieViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @Override
        public void onClick(View v) {
            //launch the details
        }

        void bindData(){
            Glide.with(inflater.getContext())
                    .fromResource()
                    .load(R.mipmap.ic_launcher)
                    .centerCrop()
                    .into(image);
            //install the rest of the data
        }
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
