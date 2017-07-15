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
    private static final int MEDIA_TYPE=2;

    private List<MovieListWrapper> wrapperList;
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

        void bindData(){}
    }

    public class MoviesViewHolder extends AbstractViewHolder{

        @BindView(R.id.title)
        TextView cardTitle;

        @BindView(R.id.media_list)
        RecyclerView additionalList;

        public MoviesViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
            additionalList.setNestedScrollingEnabled(false);
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
            case BLANK_TYPE:
                if(blank==null) blank=inflater.inflate(R.layout.blank,parent,false);
                return new AbstractViewHolder(blank);
            default:
                return new MoviesViewHolder(inflater.inflate(R.layout.movie_similar_card,parent,false));
        }
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
        return wrapperList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0) return BLANK_TYPE;
        return MEDIA_TYPE;
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
