package com.popularmovies.vpaliy.popularmoviesapp.ui.adapter;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import android.support.annotation.NonNull;
import butterknife.BindView;

public class MediaTypeAdapter extends AbstractMediaAdapter<MediaTypeAdapter.MediaTypeWrapper>{

    private static final String TAG=MediaTypeAdapter.class.getSimpleName();

    public MediaTypeAdapter(@NonNull Context context,
                            @NonNull RxBus rxBus){
        super(context,rxBus);
    }


    public class TypeViewHolder extends GenericViewHolder
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
            title.setOnClickListener(this);
            more.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(!isLocked()) {
                lock();
                MediaTypeWrapper typeWrapper = at(getAdapterPosition());
                ViewAllWrapper wrapper = ViewAllWrapper.wrap(typeWrapper.sortType, typeWrapper.mediaType);
                rxBus.send(new ViewAllEvent(wrapper));
            }
        }

        @Override
        void onBindData(){
            MediaTypeWrapper wrapper=at(getAdapterPosition());
            list.setAdapter(wrapper.adapter);
            title.setText(wrapper.text);
            more.setTextColor(wrapper.color);
        }
    }

    @Override
    public TypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root=inflater.inflate(R.layout.adapter_media_type_item,parent,false);
        return new TypeViewHolder(root);
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
