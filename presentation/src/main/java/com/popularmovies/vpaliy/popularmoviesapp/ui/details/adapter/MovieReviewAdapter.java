package com.popularmovies.vpaliy.popularmoviesapp.ui.details.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.popularmovies.vpaliy.domain.model.Review;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import java.util.ArrayList;
import java.util.List;
import at.blogc.android.views.ExpandableTextView;
import butterknife.BindView;
import android.support.annotation.NonNull;
import butterknife.ButterKnife;

public class MovieReviewAdapter extends
        RecyclerView.Adapter<MovieReviewAdapter.ReviewViewHolder> {

    private List<Review> data;
    private LayoutInflater inflater;

    public MovieReviewAdapter(@NonNull Context context){
        this.inflater=LayoutInflater.from(context);
        this.data=new ArrayList<>();
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReviewViewHolder(inflater.inflate(R.layout.adapter_movie_review,parent,false));
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        holder.onBindData();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(@NonNull List<Review> reviews){
        this.data=reviews;
        notifyDataSetChanged();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder
                implements View.OnClickListener{

        @BindView(R.id.reviewAuthor)
        TextView reviewAuthor;

        @BindView(R.id.reviewContent)
        ExpandableTextView reviewContent;

        public ReviewViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
            reviewContent.setOnClickListener(view->reviewContent.toggle());
        }

        @Override
        public void onClick(View v) {

        }

        void onBindData(){
            reviewAuthor.setText(data.get(getAdapterPosition()).getAuthor());
            reviewContent.setText(data.get(getAdapterPosition()).getContent());
        }
    }
}
