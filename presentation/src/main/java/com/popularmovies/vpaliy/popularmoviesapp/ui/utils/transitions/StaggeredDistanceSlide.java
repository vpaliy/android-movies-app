package com.popularmovies.vpaliy.popularmoviesapp.ui.utils.transitions;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.transition.TransitionValues;
import android.transition.Visibility;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import java.util.ArrayList;
import java.util.List;
import android.annotation.TargetApi;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class StaggeredDistanceSlide extends Visibility {

    private static final String PROPNAME_SCREEN_LOCATION = "android:visibility:screenLocation";

    private int spread = 1;

    public StaggeredDistanceSlide() {
        super();
    }

    @Keep
    public StaggeredDistanceSlide(Context context, AttributeSet attrs) {
        super(context, attrs);
        final TypedArray a =
                context.obtainStyledAttributes(attrs, R.styleable.StaggeredDistanceSlide);
        spread = a.getInteger(R.styleable.StaggeredDistanceSlide_spread, spread);
        a.recycle();
    }

    public int getSpread() {
        return spread;
    }

    public void setSpread(int spread) {
        this.spread = spread;
    }

    @Override
    public Animator onAppear(ViewGroup sceneRoot, View view,
                             TransitionValues startValues, TransitionValues endValues) {
        int[] position = (int[]) endValues.values.get(PROPNAME_SCREEN_LOCATION);
        return createAnimator(view, sceneRoot.getHeight() + (position[1] * spread), 0f);
    }

    @Override
    public Animator onDisappear(ViewGroup sceneRoot, View view,
                                TransitionValues startValues, TransitionValues endValues) {
        int[] position = (int[]) endValues.values.get(PROPNAME_SCREEN_LOCATION);
        return createAnimator(view, 0f, sceneRoot.getHeight() + (position[1] * spread));
    }

    private Animator createAnimator(
            final View view, float startTranslationY, float endTranslationY) {
        view.setTranslationY(startTranslationY);
        final List<Boolean> ancestralClipping = setAncestralClipping(view, false);
        Animator transition = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, endTranslationY);
        transition.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                restoreAncestralClipping(view, ancestralClipping);
            }
        });
        return transition;
    }


    public static void restoreAncestralClipping(@NonNull View view, List<Boolean> was) {
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            group.setClipChildren(was.remove(0));
        }
        ViewParent parent = view.getParent();
        if (parent != null && parent instanceof ViewGroup) {
            restoreAncestralClipping((ViewGroup) parent, was);
        }
    }


    public static List<Boolean> setAncestralClipping(@NonNull View view, boolean clipChildren) {
        return setAncestralClipping(view, clipChildren, new ArrayList<Boolean>());
    }

    private static List<Boolean> setAncestralClipping(
            @NonNull View view, boolean clipChildren, List<Boolean> was) {
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            was.add(group.getClipChildren());
            group.setClipChildren(clipChildren);
        }
        ViewParent parent = view.getParent();
        if (parent != null && parent instanceof ViewGroup) {
            setAncestralClipping((ViewGroup) parent, clipChildren, was);
        }
        return was;
    }
}