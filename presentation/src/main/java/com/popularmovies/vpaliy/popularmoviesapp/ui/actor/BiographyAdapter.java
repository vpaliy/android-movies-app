package com.popularmovies.vpaliy.popularmoviesapp.ui.actor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.popularmovies.vpaliy.domain.model.ActorInfo;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

public class BiographyAdapter extends RecyclerView.Adapter<BiographyAdapter.AbstractViewHolder>{

    private static final int INFO_TYPE=1;
    private static final int CREDIT_TYPE=2;

    private List<MovieListWrapper> wrapperList;
    private ActorInfo actorInfo;
    private LayoutInflater inflater;

    public BiographyAdapter(@NonNull Context context){
        this.inflater=LayoutInflater.from(context);
        wrapperList=new ArrayList<>();
    }

    public abstract class AbstractViewHolder extends RecyclerView.ViewHolder{
        AbstractViewHolder(View itemView){
            super(itemView);
        }
        abstract void onBindData();
    }

    public class CreditViewHolder extends AbstractViewHolder {

        @BindView(R.id.title)
        TextView cardTitle;

        @BindView(R.id.media_list)
        RecyclerView additionalList;

        public CreditViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
            additionalList.setNestedScrollingEnabled(false);
        }

        @Override
        void onBindData() {
            MovieListWrapper wrapper=wrapperList.get(getAdapterPosition()-1);
            RecyclerView.Adapter<?> adapter=wrapper.getAdapter();
            additionalList.setAdapter(adapter);
            cardTitle.setText(wrapper.getTitle());
        }
    }

    public class InfoViewHolder extends AbstractViewHolder{

        @BindView(R.id.actor_bio)
        TextView actorBio;

        InfoViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @Override
        void onBindData() {
            actorBio.setText(actorInfo.getBioDescription());
        }
    }

    @Override
    public int getItemCount() {
        return wrapperList.size()+(actorInfo==null?0:1);
    }

    @Override
    public AbstractViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case INFO_TYPE:
                View info=inflater.inflate(R.layout.layout_actor_info,parent,false);
                return new InfoViewHolder(info);
            default:
                View credit=inflater.inflate(R.layout.movie_similar_card,parent,false);
                return new CreditViewHolder(credit);
        }
    }

    public void addInfo(ActorInfo actorInfo){
        this.actorInfo=checkNotNull(actorInfo);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return position==0?INFO_TYPE:CREDIT_TYPE;
    }

    public void addWrapper(MovieListWrapper wrapper){
        wrapper=checkNotNull(wrapper);
        wrapperList.add(wrapper);
        notifyItemInserted(wrapperList.size());
    }

    @Override
    public void onBindViewHolder(AbstractViewHolder holder, int position) {
        holder.onBindData();
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
