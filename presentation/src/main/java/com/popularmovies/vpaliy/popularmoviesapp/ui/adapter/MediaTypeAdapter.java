package com.popularmovies.vpaliy.popularmoviesapp.ui.adapter;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.popularmovies.vpaliy.domain.configuration.SortType;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.ui.eventBus.RxBus;
import com.popularmovies.vpaliy.popularmoviesapp.ui.eventBus.events.RequestMoreEvent;
import com.popularmovies.vpaliy.popularmoviesapp.ui.eventBus.events.ViewAllEvent;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.wrapper.MediaType;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.wrapper.ViewAllWrapper;

import butterknife.ButterKnife;
import java.util.ArrayList;
import java.util.List;

import android.support.annotation.NonNull;
import butterknife.BindView;

public class MediaTypeAdapter extends RecyclerView.Adapter<MediaTypeAdapter.TypeViewHolder>{

    private List<MediaTypeWrapper> data;
    private LayoutInflater inflater;
    private RxBus rxBus;

    public MediaTypeAdapter(@NonNull Context context,
                            @NonNull RxBus rxBus){
        this.inflater=LayoutInflater.from(context);
        this.data=new ArrayList<>();
        this.rxBus=rxBus;
    }

    public void setData(@NonNull List<MediaTypeWrapper> wrappers){
        this.data=wrappers;
        notifyDataSetChanged();
    }

    public void addWrapper(@NonNull MediaTypeWrapper wrapper){
        int size=data.size();
        data.add(wrapper);
        notifyItemRangeInserted(size,getItemCount());
    }

    public class TypeViewHolder extends RecyclerView.ViewHolder
                implements View.OnClickListener{

        @BindView(R.id.media_list)
        RecyclerView list;

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.more)
        TextView more;

        public TypeViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
            list.setLayoutManager(new LinearLayoutManager(itemView.getContext(),
                    LinearLayoutManager.HORIZONTAL,false));
            
            list.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    LinearLayoutManager layoutManager=LinearLayoutManager.class.cast(recyclerView.getLayoutManager());
                    int totalItemCount = layoutManager.getItemCount();
                    int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

                    boolean endHasBeenReached = lastVisibleItemPosition + 5 >= totalItemCount;
                    if (totalItemCount > 0 && endHasBeenReached) {
                        RequestMoreEvent event=RequestMoreEvent.createRequest(at(getAdapterPosition()).sortType);
                        rxBus.send(event);
                    }
                }
            });
            list.setNestedScrollingEnabled(false);
        }

        @Override
        public void onClick(View v) {
            MediaTypeWrapper typeWrapper=at(getAdapterPosition());
            ViewAllWrapper wrapper=ViewAllWrapper.wrap(typeWrapper.sortType,typeWrapper.mediaType);
            rxBus.send(new ViewAllEvent(wrapper));
        }

        void bindData(){
            MediaTypeWrapper wrapper=at(getAdapterPosition());
            list.setAdapter(wrapper.adapter);
            title.setText(wrapper.text);
            more.setTextColor(wrapper.color);
        }
    }

    private MediaTypeWrapper at(int index){
        return data.get(index);
    }

    @Override
    public TypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root=inflater.inflate(R.layout.adapter_media_type_item,parent,false);
        return new TypeViewHolder(root);
    }

    @Override
    public void onBindViewHolder(TypeViewHolder holder, int position) {
        holder.bindData();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MediaTypeWrapper {
        private final String text;
        private final int color;
        private final SortType sortType;
        private final MediaType mediaType;
        private final RecyclerView.Adapter<?> adapter;

        private MediaTypeWrapper(@NonNull String text,
                                 @NonNull RecyclerView.Adapter<?> adapter,
                                 @NonNull SortType sortType,
                                 @NonNull MediaType mediaType,
                                 int color){
            this.text=text;
            this.adapter=adapter;
            this.sortType=sortType;
            this.color=color;
            this.mediaType=mediaType;
        }

        public static MediaTypeWrapper wrap(@NonNull String text,
                                            @NonNull SortType sortType,
                                            @NonNull RecyclerView.Adapter<?> adapter,
                                            @NonNull MediaType mediaType,
                                            int color){
            return new MediaTypeWrapper(text,adapter,sortType,mediaType,color);
        }
    }
}
