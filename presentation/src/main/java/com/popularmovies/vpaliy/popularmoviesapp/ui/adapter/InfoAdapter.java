package com.popularmovies.vpaliy.popularmoviesapp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.popularmovies.vpaliy.domain.model.MovieInfo;
import com.popularmovies.vpaliy.popularmoviesapp.R;

import java.util.ArrayList;
import java.util.List;

import at.blogc.android.views.ExpandableTextView;
import butterknife.BindView;
import butterknife.ButterKnife;


public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.AbstractViewHolder> {

    private static final int INFO_TYPE=1;
    private static final int MEDIA_TYPE=2;

    private List<MovieListWrapper> wrapperList;
    private MovieInfo movieInfo;

    private final LayoutInflater inflater;
    private final Context context;

    public InfoAdapter(@NonNull Context context){
        this.inflater=LayoutInflater.from(context);
        this.context=context;
        this.wrapperList=new ArrayList<>();
    }


    public abstract class AbstractViewHolder extends RecyclerView.ViewHolder {

        public AbstractViewHolder(View itemView){
            super(itemView);
        }

        abstract void bindData();
    }


    public class InfoViewHolder extends AbstractViewHolder {

        @BindView(R.id.budget)
        TextView movieBudget;

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

        void bindData(){
            final Context context=inflater.getContext();
            final String NA=context.getString(R.string.NA);
            final String budgetText=movieInfo.getBudget()!=null?"$"+movieInfo.getBudget():NA;
            final String revenueText=movieInfo.getRevenue()!=null?"$"+movieInfo.getRevenue():NA;
            final String releaseDateText=movieInfo.getReleaseDate()!=null?movieInfo.getReleaseDate().toString():NA;
            final String descriptionText=movieInfo.getDescription()!=null?movieInfo.getDescription():NA;

            movieDescription.setText(descriptionText);
            movieBudget.setText(budgetText);
            movieRevenue.setText(revenueText);
            movieReleaseDate.setText(releaseDateText);
        }
    }


    public class MoviesViewHolder extends AbstractViewHolder{

        @BindView(R.id.cardTitle)
        TextView cardTitle;

        @BindView(R.id.additionalList)
        RecyclerView additionalList;

        public MoviesViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
            additionalList.setNestedScrollingEnabled(false);
            additionalList.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));

        }

        @Override
        void bindData() {
            MovieListWrapper wrapper=wrapperList.get(getAdapterPosition()-1);
            RecyclerView.Adapter<?> adapter=wrapper.getAdapter();
            additionalList.setAdapter(adapter);
            cardTitle.setText(wrapper.getTitle());
        }
    }


    @Override
    public AbstractViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case INFO_TYPE:
                return new InfoViewHolder(inflater.inflate(R.layout.movie_info_card,parent,false));
            case MEDIA_TYPE:
                return new MoviesViewHolder(inflater.inflate(R.layout.movie_similar_card,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(AbstractViewHolder holder, int position) {
        holder.bindData();
    }

    @Override
    public int getItemCount() {
        return wrapperList.size()+(movieInfo!=null?1:0);
    }

    @Override
    public int getItemViewType(int position) {
        return position==0?INFO_TYPE:MEDIA_TYPE;
    }


    public void setMovieInfo(MovieInfo movieInfo){
        this.movieInfo=movieInfo;
        notifyDataSetChanged();
    }

    public void addWrapper(MovieListWrapper wrapper){
        wrapperList.add(wrapper);
        notifyItemInserted(wrapperList.size());
    }


    public static class MovieListWrapper {

        private RecyclerView.Adapter<?> adapter;
        private String title;

        private MovieListWrapper(RecyclerView.Adapter<?> adapter, String title){
            this.adapter=adapter;
            this.title=title;
        }

        public RecyclerView.Adapter<?> getAdapter() {
            return adapter;
        }

        public String getTitle() {
            return title;
        }

        public static MovieListWrapper wrap(RecyclerView.Adapter<?> adapter, String title){
            return new MovieListWrapper(adapter,title);
        }
    }

}
