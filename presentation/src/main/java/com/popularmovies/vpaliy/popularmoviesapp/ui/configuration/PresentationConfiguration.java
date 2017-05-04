package com.popularmovies.vpaliy.popularmoviesapp.ui.configuration;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;
import com.popularmovies.vpaliy.popularmoviesapp.R;

import android.support.annotation.NonNull;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PresentationConfiguration
        implements SharedPreferences.OnSharedPreferenceChangeListener{

    private final String GRID_PRESENTATION;
    private final String CARD_PRESENTATION;
    private final String PRESENTATION_KEY;

    private Presentation presentation;
    private SharedPreferences sharedPreferences;

    @Inject
    public PresentationConfiguration(@NonNull Context context){
        this.sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        this.sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        this.PRESENTATION_KEY=context.getString(R.string.presentation_key);
        final String[] representations=context.getResources().getStringArray(R.array.presentation);
        GRID_PRESENTATION=representations[0];
        CARD_PRESENTATION=representations[1];
        this.presentation=assignView(PRESENTATION_KEY,CARD_PRESENTATION);
    }

    private Presentation assignView(String key,String defValue){
        String value=sharedPreferences.getString(key,defValue);
        if(GRID_PRESENTATION.equals(value)){
            return Presentation.GRID;
        }else if(CARD_PRESENTATION.equals(value)){
            return Presentation.CARD;
        }
        return Presentation.LIST;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(PRESENTATION_KEY.equals(key)){
            presentation=assignView(PRESENTATION_KEY,GRID_PRESENTATION);
        }
    }

    public void savePresentation(Presentation presentation){
        this.presentation=presentation;
        switch (presentation){
            case GRID:
                sharedPreferences.edit().putString(PRESENTATION_KEY,GRID_PRESENTATION).apply();
                break;
            case CARD:
                sharedPreferences.edit().putString(PRESENTATION_KEY,CARD_PRESENTATION).apply();
                break;
        }
    }


    public Presentation getPresentation() {
        return presentation;
    }

   public enum Presentation{
        GRID,
        CARD,
        LIST
    }
}
