package com.popularmovies.vpaliy.popularmoviesapp.ui.details.adapter;

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

    private static final int BLANK_TYPE=0;
    private static final int INFO_TYPE=1;
    private static final int MEDIA_TYPE=2;

    private List<MovieListWrapper> wrapperList;
    private MovieInfo movieInfo;
    private View blank;

    private final LayoutInflater inflater;
    private final Context context;

    public InfoAdapter(@NonNull Context context){
        this.inflater=LayoutInflater.from(context);
        this.context=context;
        this.wrapperList=new ArrayList<>();
    }


    public class AbstractViewHolder extends RecyclerView.ViewHolder {

        public AbstractViewHolder(View itemView){
            super(itemView);
        }

        void bindData(){};
    }



    public class InfoViewHolder extends AbstractViewHolder
            implements View.OnClickListener{

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
            movieDescription.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            movieDescription.toggle();
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
            MovieListWrapper wrapper=wrapperList.get(getAdapterPosition()-2);
            RecyclerView.Adapter<?> adapter=wrapper.getAdapter();
            additionalList.setAdapter(adapter);
            cardTitle.setText(wrapper.getTitle());
        }
    }


    @Override
    public AbstractViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case BLANK_TYPE:
                if(blank==null) blank=inflater.inflate(R.layout.blank,parent,false);
                return new AbstractViewHolder(blank);
            case INFO_TYPE:
                return new InfoViewHolder(inflater.inflate(R.layout.movie_info_card,parent,false));
            case MEDIA_TYPE:
                return new MoviesViewHolder(inflater.inflate(R.layout.movie_similar_card,parent,false));
        }
        return null;
    }

    public View getBlank() {
        return blank;
    }

    @Override
    public void onBindViewHolder(AbstractViewHolder holder, int position) {
        holder.bindData();
    }

    @Override
    public int getItemCount() {
        return wrapperList.size()+(movieInfo!=null?1:0)+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0) return BLANK_TYPE;
        return position>1?MEDIA_TYPE:INFO_TYPE;
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
