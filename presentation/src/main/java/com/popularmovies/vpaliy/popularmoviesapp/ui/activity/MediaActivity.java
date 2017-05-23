package com.popularmovies.vpaliy.popularmoviesapp.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.App;
import com.popularmovies.vpaliy.popularmoviesapp.ui.adapter.MovieTypeAdapter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.eventBus.events.ExposeDetailsEvent;
import com.popularmovies.vpaliy.popularmoviesapp.ui.fragment.MoviesFragment;
import com.popularmovies.vpaliy.popularmoviesapp.ui.fragment.TvShowsFragment;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Permission;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuItem;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;

import butterknife.BindView;

import butterknife.ButterKnife;

public class MediaActivity extends BaseActivity
        implements SharedPreferences.OnSharedPreferenceChangeListener{

    private static final String TAG=MediaActivity.class.getSimpleName();

    @BindView(R.id.actionBar)
    protected Toolbar actionBar;

    @BindView(R.id.drawerLayout)
    protected DrawerLayout drawerLayout;

    @BindView(R.id.navigation)
    protected NavigationView navigationView;

    @BindView(R.id.bottom_navigation)
    protected BottomBar bottomNavigation;

    @BindView(R.id.media_frame)
    protected FrameLayout mediaFrame;

    protected ViewPager movieTypePager;

    private ActionBarDrawerToggle drawerToggle;

    private boolean isMenuVisible;

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
            final Fragment fragment;
            switch (tabId) {
                case R.id.movies:
                    fragment = new MoviesFragment();
                    break;
                default:
                    fragment = new TvShowsFragment();
                    break;
            }
            if(Permission.checkForVersion(Build.VERSION_CODES.LOLLIPOP)) {
                TransitionManager.beginDelayedTransition(mediaFrame,new Fade());
            }
            new Handler().post(()->getSupportFragmentManager().beginTransaction()
                    .replace(R.id.media_frame,fragment)
                    .commitNow());
        }));
    }

    private void setMovieTypePager(){
        MovieTypeAdapter typeAdapter=new MovieTypeAdapter(getSupportFragmentManager(),this);
        movieTypePager.setAdapter(typeAdapter);
    }

    private void setActionBar(){
        setSupportActionBar(actionBar);
        ActionBar actionBar=getSupportActionBar();
        navigationView.setCheckedItem(R.id.movies);
        isMenuVisible=true;
        if(actionBar!=null) {
            switch (iSortConfiguration.getConfiguration()) {
                case POPULAR:
                    actionBar.setTitle(R.string.ByPopularity);
                    break;
                case TOP_RATED:
                    actionBar.setTitle(R.string.ByTopRated);
                    break;
                case FAVORITE:
                    navigationView.setCheckedItem(R.id.favorites);
                    isMenuVisible=false;
                    actionBar.setTitle(R.string.ByFavorite);
            }
        }
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
                case R.id.favorites:
                    actionBar.setTitle(R.string.ByFavorite);
                    drawerLayout.closeDrawers();
                    isMenuVisible=false;
                    supportInvalidateOptionsMenu();
                    return true;
                case R.id.movies:
                    actionBar.setTitle(R.string.ByPopularity);
                    drawerLayout.closeDrawers();
                    isMenuVisible=true;
                    supportInvalidateOptionsMenu();
                    return true;
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
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if(menu!=null){
            MenuItem item=menu.findItem(R.id.sortAction);
            if(item!=null) item.setVisible(isMenuVisible);
            return true;
        }
        return false;
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
        }
    }

    private void showDetails(@NonNull ExposeDetailsEvent event){
        navigator.showDetails(this,event);

    }
}
