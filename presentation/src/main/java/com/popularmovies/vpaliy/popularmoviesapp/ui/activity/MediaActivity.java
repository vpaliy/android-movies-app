package com.popularmovies.vpaliy.popularmoviesapp.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.App;
import com.popularmovies.vpaliy.popularmoviesapp.ui.adapter.MediaTypePagerAdapter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.eventBus.events.ExposeDetailsEvent;
import com.popularmovies.vpaliy.popularmoviesapp.ui.eventBus.events.ViewAllEvent;
import com.popularmovies.vpaliy.popularmoviesapp.ui.view.MediaPager;
import com.roughike.bottombar.BottomBar;

import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.ButterKnife;
import android.view.ViewGroup;
import butterknife.BindView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class MediaActivity extends BaseActivity
        implements SharedPreferences.OnSharedPreferenceChangeListener{


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
        setContentView(R.layout.activity_movies);
        ButterKnife.bind(this);
        setUI(savedInstanceState);
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);
    }

    private void setBottomNavigation(){
        bottomNavigation.setOnTabSelectListener((tabId -> {
            mediaPager.animate()
                    .alpha(0)
                    .setDuration(200)
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
                                    .setDuration(200)
                                    .setListener(null).start();
                        }
                    }).start();
        }));
    }

    private void setMovieTypePager(){
        mediaPager.setAdapter(new MediaTypePagerAdapter(getSupportFragmentManager(),this));
        mediaPager.setOffscreenPageLimit(10);
    }

    private void setActionBar(){
        ViewGroup.MarginLayoutParams params= ViewGroup.MarginLayoutParams.class.cast(actionBar.getLayoutParams());
        params.topMargin=getStatusBarHeight();
        setSupportActionBar(actionBar);
        ActionBar actionBar=getSupportActionBar();
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


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        // moviesFragment.onConfigChanged();
    }

    private void setUI(Bundle savedInstanceState){
        setDrawer();
        setActionBar();
        setMovieTypePager();
        setBottomNavigation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager
                .getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_movies,menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item)
                ||super.onOptionsItemSelected(item);
    }

    @Override
    void inject() {
        App.appInstance().appComponent().inject(this);
    }

    @Override
    void handleEvent(@NonNull Object event) {
        if(event instanceof  ExposeDetailsEvent){
            showDetails(ExposeDetailsEvent.class.cast(event));
        }else if(event instanceof ViewAllEvent){
            viewAll(ViewAllEvent.class.cast(event));
        }
    }

    private void showDetails(@NonNull ExposeDetailsEvent event){
        navigator.showDetails(this,event);
    }

    private void viewAll(@NonNull ViewAllEvent event){
        navigator.viewAll(this,event);
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}