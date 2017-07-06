package com.popularmovies.vpaliy.popularmoviesapp.ui.activity;

import android.os.Bundle;
import com.popularmovies.vpaliy.domain.configuration.SortType;
import com.popularmovies.vpaliy.popularmoviesapp.App;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.bus.events.ExposeDetailsEvent;
import com.popularmovies.vpaliy.popularmoviesapp.ui.fragment.MoreMediaFragment;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Constants;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.wrapper.MediaType;
import butterknife.ButterKnife;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

public class MoreMediaActivity extends BaseActivity {

    private SortType sortType;
    private MediaType mediaType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_media);
        ButterKnife.bind(this);
        handleBundle(savedInstanceState);
        setUI();
    }

    private void handleBundle(Bundle bundle){
        if(bundle==null) bundle=getIntent().getExtras();
        sortType=SortType.valueOf(bundle.getString(Constants.EXTRA_SORT_TYPE));
        mediaType=MediaType.valueOf(bundle.getString(Constants.EXTRA_MEDIA_TYPE));
    }

    private void setUI(){
        setActionBar();
        MoreMediaFragment fragment;
        switch (mediaType){
            case MOVIES:
                fragment=MoreMediaFragment.create(sortType,false);
                break;
            default:
                fragment=MoreMediaFragment.create(sortType,true);
                break;
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.media_frame,fragment).commit();
    }

    private void setActionBar(){
        Toolbar actionBar=ButterKnife.findById(this,R.id.actionBar);
        setSupportActionBar(actionBar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            actionBar.setNavigationOnClickListener(v->onBackPressed());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.EXTRA_SORT_TYPE,sortType.name());
        outState.putString(Constants.EXTRA_MEDIA_TYPE,mediaType.name());
    }

    @Override
    void inject() {
        App.appInstance().appComponent() .inject(this);
    }

    @Override
    void handleEvent(@NonNull Object event) {
        if(event instanceof ExposeDetailsEvent) {
            showDetails(ExposeDetailsEvent.class.cast(event));
        }
    }

    private void showDetails(@NonNull ExposeDetailsEvent event){
        navigator.showDetails(this,event);
    }
}
