package com.popularmovies.vpaliy.popularmoviesapp.ui.actor;


import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.popularmovies.vpaliy.data.entity.ActorDetailEntity;
import com.popularmovies.vpaliy.domain.model.ActorDetails;
import com.popularmovies.vpaliy.popularmoviesapp.App;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerViewComponent;
import com.popularmovies.vpaliy.popularmoviesapp.di.module.PresenterModule;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseFragment;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Constants;
import com.vpaliy.chips_lover.ChipBuilder;

import java.util.List;

import javax.inject.Inject;
import butterknife.BindView;

import static com.popularmovies.vpaliy.popularmoviesapp.ui.actor.ActorContract.Presenter;

public class ActorFragment extends BaseFragment
        implements ActorContract.View{

    private Presenter presenter;
    private int actorId;

    @BindView(R.id.actor_backdrop)
    protected ImageView actorBackdrop;

    @BindView(R.id.actor_image)
    protected ImageView actorImage;

    @BindView(R.id.diagonalLayout)
    protected View diagonalLayout;

    @BindView(R.id.parent)
    protected View parent;

    @BindView(R.id.actor_name)
    protected TextView actorName;

    public static ActorFragment newInstance(Bundle args){
        ActorFragment fragment=new ActorFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_actor, container, false);
        bind(root);
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState==null){
            savedInstanceState=getArguments();
        }
        actorId=savedInstanceState.getInt(Constants.EXTRA_ID);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(view!=null){
            getActivity().supportPostponeEnterTransition();
            presenter.start(actorId);
        }
    }

    @Override
    public void initializeDependencies() {
        DaggerViewComponent.builder()
                .applicationComponent(App.appInstance().appComponent())
                .presenterModule(new PresenterModule())
                .build().inject(this);
    }

    @Override
    public void showBackground(@NonNull String backdropPath) {
        Glide.with(getContext())
                .load(backdropPath)
                .asBitmap()
                .priority(Priority.IMMEDIATE)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(new ImageViewTarget<Bitmap>(actorBackdrop) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        actorBackdrop.setImageBitmap(resource);
                        new Palette.Builder(resource).generate(ActorFragment.this::applyPalette);
                    }
                });
    }

    @Inject
    @Override
    public void attachPresenter(@NonNull Presenter presenter) {
        this.presenter=presenter;
        this.presenter.attachView(this);
    }

    @Override
    public void showBioDetails(@NonNull ActorDetails entity) {
        actorName.setText(entity.getActorCover().getName());
    }

    @Override
    public void showImages(@NonNull List<String> images) {

    }

    @Override
    public void showProfilePhoto(@NonNull String profilePhoto) {
        Glide.with(getContext())
                .load(profilePhoto)
                .asBitmap()
                .priority(Priority.IMMEDIATE)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(new ImageViewTarget<Bitmap>(actorImage) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        actorImage.setImageBitmap(resource);
                        actorImage.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                            @Override
                            public boolean onPreDraw() {
                                actorImage.getViewTreeObserver().removeOnPreDrawListener(this);
                                getActivity().supportStartPostponedEnterTransition();
                                return true;
                            }
                        });
                    }
                });
    }

    private void applyPalette(Palette palette){
        Palette.Swatch swatch=palette.getDarkMutedSwatch();
        if(swatch==null) swatch=palette.getDominantSwatch();
        //apply if not null
        if(swatch!=null){
            parent.setBackgroundColor(swatch.getRgb());
            actorName.setTextColor(swatch.getTitleTextColor());
        }
    }

    private void setDrawableColor(TextView view, int color){
        Drawable[] drawables=view.getCompoundDrawables();
        for(Drawable drawable:drawables){
            if(drawable!=null){
                drawable.mutate();
                DrawableCompat.setTint(drawable,color);
            }
        }
    }
    private void loadImage(String imageUrl, ImageView target){
        Glide.with(getContext())
                .load(imageUrl)
                .priority(Priority.IMMEDIATE)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(target);
    }
}
