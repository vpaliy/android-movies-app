package com.popularmovies.vpaliy.popularmoviesapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.App;
import com.popularmovies.vpaliy.popularmoviesapp.ui.eventBus.events.ExposeDetailsEvent;
import com.popularmovies.vpaliy.popularmoviesapp.ui.fragment.MoviesFragment;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import butterknife.BindView;

import butterknife.ButterKnife;
import static com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Constants.MOVIES_TAG;

public class MoviesActivity extends BaseActivity {

    private static final String TAG=MoviesActivity.class.getSimpleName();

    @BindView(R.id.actionBar)
    protected Toolbar actionBar;

    @BindView(R.id.drawerLayout)
    protected DrawerLayout drawerLayout;

    @BindView(R.id.navigation)
    protected NavigationView navigationView;

    private IMoviesFragment moviesFragment;

    private ActionBarDrawerToggle drawerToggle;

    private boolean isMenuVisible;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        ButterKnife.bind(this);
        setDrawer();
        setActionBar();

        if(moviesFragment==null) {
            setUI();
        }

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
                        moviesFragment.sort(ISortConfiguration.SortType.FAVORITE);
                        drawerLayout.closeDrawers();
                        isMenuVisible=false;
                        supportInvalidateOptionsMenu();
                        return true;
                    case R.id.movies:
                        actionBar.setTitle(R.string.ByPopularity);
                        moviesFragment.sort(ISortConfiguration.SortType.POPULAR);
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



    private void setUI(){
        MoviesFragment fragment=new MoviesFragment();
        moviesFragment=fragment;
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.movies, fragment, MOVIES_TAG)
                .commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movies,menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(menu!=null){
            MenuItem item=menu.findItem(R.id.sortAction);
            if(item!=null) item.setVisible(isMenuVisible);
            return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getGroupId()==R.id.sortingChoice) {
            switch (item.getItemId()) {
                case R.id.byPopularity:
                    actionBar.setTitle(R.string.ByPopularity);
                    moviesFragment.sort(ISortConfiguration.SortType.POPULAR);
                    break;
                case R.id.byLatest:
                    actionBar.setTitle(R.string.ByTopRated);
                    moviesFragment.sort(ISortConfiguration.SortType.TOP_RATED);
                    break;
            }
            return true;
        }else {
            switch (item.getItemId()) {
                case R.id.sortAction:
                    //expand the choices
                    return true;
            }
        }
        return true;
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

    public interface IMoviesFragment {
        void sort(ISortConfiguration.SortType sortType);
    }

}
