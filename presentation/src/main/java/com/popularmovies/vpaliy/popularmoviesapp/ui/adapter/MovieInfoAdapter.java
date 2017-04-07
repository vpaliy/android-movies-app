package com.popularmovies.vpaliy.popularmoviesapp.ui.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.popularmovies.vpaliy.domain.model.MovieInfo;
import com.popularmovies.vpaliy.popularmoviesapp.R;

import at.blogc.android.views.ExpandableTextView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieInfoAdapter extends RecyclerView.Adapter<MovieInfoAdapter.InfoViewHolder> {

    private LayoutInflater inflater;
    private MovieInfo movieInfo;

    public MovieInfoAdapter(@NonNull Context context,
                            @NonNull MovieInfo movieInfo){
        this.inflater=LayoutInflater.from(context);
        this.movieInfo=movieInfo;
    }

    public class InfoViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.budget)
        TextView movieBudget;

        @BindView(R.id.directedBy)
        TextView movieDirectedBy;

        @BindView(R.id.revenue)
        TextView movieRevenue;

        @BindView(R.id.releaseDate)
        TextView movieReleaseDate;

        @BindView(R.id.movieDescription)
        ExpandableTextView movieDescription;


        public InfoViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        void onBindData(){
            final Context context=inflater.getContext();
            final String NA=context.getString(R.string.NA);
            final String budgetText=movieInfo.getBudget()!=null?movieInfo.getBudget():NA;
            final String directedByText=movieInfo.getDirector()!=null?movieInfo.getDirector():NA;
            final String revenueText=movieInfo.getRevenue()!=null?movieInfo.getRevenue():NA;
            final String releaseDateText=movieInfo.getReleaseDate()!=null?movieInfo.getReleaseDate().toString():NA;
            final String descriptionText=movieInfo.getDescription()!=null?movieInfo.getDescription():NA;

            movieDescription.setText(descriptionText);
            movieBudget.setText(budgetText);
            movieDirectedBy.setText(directedByText);
            movieRevenue.setText(revenueText);
            movieReleaseDate.setText(releaseDateText);
        }

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    @Override
    public InfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new InfoViewHolder(inflater.inflate(R.layout.movie_info_card,parent,false));
    }

    @Override
    public void onBindViewHolder(InfoViewHolder holder, int position) {
        holder.onBindData();
    }
}
