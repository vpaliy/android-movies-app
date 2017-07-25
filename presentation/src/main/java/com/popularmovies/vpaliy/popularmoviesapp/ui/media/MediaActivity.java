package com.popularmovies.vpaliy.popularmoviesapp.ui.media;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.App;
import com.popularmovies.vpaliy.popularmoviesapp.ui.settings.SettingsActivity;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.bus.events.ExposeEvent;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.bus.events.ViewAllEvent;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseActivity;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Constants;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.PresentationUtils;
import com.popularmovies.vpaliy.popularmoviesapp.ui.view.MediaPager;
import com.roughike.bottombar.BottomBar;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.ButterKnife;
import butterknife.BindView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

public class MediaActivity extends BaseActivity {
    
    @BindView(R.id.actionBar)
    protected Toolbar actionBar;

    @BindView(R.id.drawerLayout)
    protected DrawerLayout drawerLayout;

    @BindView(R.id.navigation)
    protected NavigationView navigationView;

    @BindView(R.id.bottom_navigation)
    protected BottomBar bottomNavigation;

    @BindView(R.id.media_pager)
    protected MediaPager mediaPager;

    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        ButterKnife.bind(this);
        setUI();
    }

    private void setBottomNavigation(){
        final int duration=getResources().getInteger(R.integer.page_fade_duration);
        bottomNavigation.setOnTabSelectListener((tabId ->
                mediaPager.animate()
                        .alpha(0)
                        .setDuration(duration)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                switch (tabId){
                                    case R.id.movies:
                                        actionBar.setTitle(R.string.movies);
                                        mediaPager.setCurrentItem(0,false);
                                        break;
                                    case R.id.tv_shows:
                                        actionBar.setTitle(R.string.tv_shows);
                                        mediaPager.setCurrentItem(1,false);
                                        break;
                                    case R.id.personal:
                                        actionBar.setTitle(R.string.personal);
                                        mediaPager.setCurrentItem(2,false);
                                }
                                mediaPager.animate()
                                        .alpha(1.f)
                                        .setDuration(duration)
                                        .setListener(null).start();
                            }
                        }).start()));
    }

    private void setMovieTypePager(){
        mediaPager.setAdapter(new MediaTypePagerAdapter(getSupportFragmentManager(),this));
        mediaPager.setOffscreenPageLimit(3);
    }

    private void setActionBar(){
        int statusBarHeight= PresentationUtils.getStatusBarHeight(getResources());
        actionBar.getLayoutParams().height+=statusBarHeight;
        actionBar.setPadding(0,statusBarHeight,0,0);
        setSupportActionBar(actionBar);
        navigationView.setCheckedItem(R.id.movies);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    private void setDrawer(){
        this.drawerToggle=new ActionBarDrawerToggle(this,drawerLayout,actionBar,0,0);
        drawerLayout.setDrawerListener(drawerToggle);
        navigationView.setNavigationItemSelectedListener(item -> {
            item.setChecked(true);
            switch (item.getItemId()){
                case R.id.settings:
                    startActivity(new Intent(this,SettingsActivity.class));
                    return true;

            }
            return false;
        });
    }

    private void setUI(){
        setDrawer();
        setActionBar();
        setMovieTypePager();
        setBottomNavigation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_movies,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       return drawerToggle.onOptionsItemSelected(item) ||super.onOptionsItemSelected(item);
    }

    @Override
    public void inject() {
        App.appInstance().appComponent().inject(this);
    }

    @Override
    public void handleEvent(@NonNull Object event) {
        if(event instanceof ExposeEvent){
            Log.d(MediaActivity.class.getSimpleName(),"HandleEvent");
            showDetails(ExposeEvent.class.cast(event));
        }else if(event instanceof ViewAllEvent){
            viewAll(ViewAllEvent.class.cast(event));
        }
    }

    private void showDetails(@NonNull ExposeEvent event){
        disposables.clear();
        event.data.putInt(Constants.EXTRA_TRANSITION_ID,R.transition.details_poster_enter);
        navigator.navigate(this,event);
    }

    private void viewAll(@NonNull ViewAllEvent event){
        navigator.viewAll(this,event);
    }
}