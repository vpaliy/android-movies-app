package com.popularmovies.vpaliy.popularmoviesapp.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.robertlevonyan.views.chip.Chip;

import java.util.ArrayList;
import java.util.List;

public class ChipsContainerView extends LinearLayout {

    private List<ChipView> chips;
    private AttributeSet attrs;

    public ChipsContainerView(Context context){
        this(context,null,0);
    }

    public ChipsContainerView(Context context, AttributeSet attrs){
        this(context,attrs,0);
    }

    public ChipsContainerView(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
        this.attrs=attrs;
    }

    public void setTags(List<String> tags){
        if(tags==null||tags.isEmpty()) return;
        if(chips==null){
            chips=new ArrayList<>(tags.size());
        }
        if(tags.size()>chips.size()){
            int diff=tags.size()-chips.size();
            for(int index=0;index<diff;index++) {
                ChipView chip = new ChipView(getContext());
                chips.add(chip);
                addView(chip);
            }
        }
        int index=0;
        for(;index<tags.size();index++){
            ChipView chip=chips.get(index);
            chip.setChipText(tags.get(index));
            if(chip.getVisibility()!= View.VISIBLE){
                chip.setVisibility(View.VISIBLE);
            }
        }

        if(index<chips.size()){
            for(;index<chips.size();index++){
                chips.get(index).setVisibility(View.GONE);
            }
        }
    }

}
