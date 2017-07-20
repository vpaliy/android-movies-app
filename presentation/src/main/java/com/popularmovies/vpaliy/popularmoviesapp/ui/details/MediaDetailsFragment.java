package com.popularmovies.vpaliy.popularmoviesapp.ui.details;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import butterknife.ButterKnife;
import io.codetail.animation.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.popularmovies.vpaliy.domain.model.ActorCover;
import com.popularmovies.vpaliy.domain.model.MediaCollection;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import com.popularmovies.vpaliy.domain.model.MovieDetails;
import com.popularmovies.vpaliy.domain.model.SeasonCover;
import com.popularmovies.vpaliy.domain.model.Trailer;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseFragment;
import com.popularmovies.vpaliy.popularmoviesapp.ui.details.adapter.InfoAdapter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.details.adapter.MovieBackdropsAdapter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.details.adapter.MovieCastAdapter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.details.adapter.MovieTrailersAdapter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.details.adapter.RelatedMoviesAdapter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.details.adapter.SeasonAdapter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Constants;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.PresentationUtils;
import com.popularmovies.vpaliy.popularmoviesapp.ui.view.ElasticDismissLayout;
import com.popularmovies.vpaliy.popularmoviesapp.ui.view.FABToggle;
import com.popularmovies.vpaliy.popularmoviesapp.ui.view.ParallaxImageView;
import com.popularmovies.vpaliy.popularmoviesapp.ui.view.ParallaxRatioViewPager;
import com.popularmovies.vpaliy.popularmoviesapp.ui.view.TranslatableLayout;
import com.rd.PageIndicatorView;
import com.rd.animation.type.AnimationType;
import com.vpaliy.chips_lover.ChipBuilder;
import com.vpaliy.chips_lover.ChipsLayout;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.popularmovies.vpaliy.popularmoviesapp.ui.details.MediaDetailsContract.Presenter;

public abstract class MediaDetailsFragment extends BaseFragment
        implements MediaDetailsContract.View {

    protected Presenter presenter;

    @BindView(R.id.backdrop_pager)
    protected ParallaxRatioViewPager pager;

    @BindView(R.id.page_indicator)
    protected PageIndicatorView indicatorView;

    @BindView(R.id.back_wrapper)
    protected View actionBar;

    @BindView(R.id.details_background)
    protected TranslatableLayout detailsParent;

    @BindView(R.id.media_title)
    protected TextView mediaTitle;

    @BindView(R.id.media_ratings)
    protected TextView mediaRatings;

    @BindView(R.id.chipsContainer)
    protected ChipsLayout tags;

    @BindView(R.id.media_release_year)
    protected TextView releaseYear;

    @BindView(R.id.media_description)
    protected TextView mediaDescription;

    @BindView(R.id.details)
    protected RecyclerView info;

    @BindView(R.id.poster)
    protected ParallaxImageView poster;

    @BindView(R.id.share_fab)
    protected FABToggle toggle;

    @BindView(R.id.media_duration)
    protected TextView duration;

    @BindView(R.id.media_money)
    protected TextView money;

    @BindView(R.id.parent)
    protected ElasticDismissLayout parent;

    private InfoAdapter infoAdapter;
    private MovieBackdropsAdapter adapter;

    private String mediaId;
    private String sharedPath;
    private String sharedPosterPath;


    public static MediaDetailsFragment newInstance(Bundle args){
        boolean isTvShow=args.getBoolean(Constants.EXTRA_IS_TV,false);
        MediaDetailsFragment fragment=isTvShow?new TvDetailsFragment():new MovieDetailsFragment();
        args.getBoolean(Constants.EXTRA_IS_TV,false);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if(savedInstanceState==null) savedInstanceState=getArguments();
        mediaId=savedInstanceState.getString(Constants.EXTRA_ID);
        sharedPath=savedInstanceState.getString(Constants.EXTRA_DATA);
        sharedPosterPath=savedInstanceState.getString(Constants.EXTRA_POSTER_PATH);
        initializeDependencies();
    }

    @Override
    public void showMoney(@NonNull String money) {
        this.money.setText(money);
    }

    @Override
    public void showDuration(@NonNull String duration) {
        this.duration.setText(duration);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_media_details,container,false);
        bind(root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(view!=null){
            if(sharedPosterPath!=null){
                ViewCompat.setTransitionName(poster,getArguments().getString(Constants.EXTRA_TRANSITION_NAME));
                loadCover();
            }
            getActivity().supportPostponeEnterTransition();
            infoAdapter=new InfoAdapter(getContext());
            adapter=new MovieBackdropsAdapter(getContext());
            adapter.setData(Collections.singletonList(sharedPath));
            adapter.setCallback((image,bitmap)->{
                indicatorView.setAnimationType(AnimationType.WORM);
                indicatorView.setTranslationY(image.getHeight()-indicatorView.getHeight()*2.5f);
                detailsParent.setStaticOffset(image.getHeight());
                detailsParent.setOffset(image.getHeight());
                detailsParent.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                    @Override
                    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                        View blank=infoAdapter.getBlank();
                        ViewGroup.LayoutParams params=blank.getLayoutParams();
                        params.height=image.getHeight()+detailsParent.getHeight();
                        blank.setLayoutParams(params);
                        int offset = image.getHeight() + detailsParent.getHeight() - (toggle.getHeight() / 2);
                        toggle.setStaticOffset(offset);
                        toggle.setOffset(offset);
                    }
                });
                final float posterOffset=image.getHeight()-poster.getHeight()*.33f;
                poster.setMinOffset(ViewCompat.getMinimumHeight(image)+poster.getHeight()/2);
                poster.setStaticOffset(posterOffset);
                poster.setOffset(posterOffset);
                poster.setPinned(true);
                toggle.setMinOffset(ViewCompat.getMinimumHeight(pager)-toggle.getHeight()/2);
                new Palette.Builder(bitmap).generate(MediaDetailsFragment.this::applyPalette);
                image.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        image.getViewTreeObserver().removeOnPreDrawListener(this);
                        return true;
                    }
                });
                presenter.start(mediaId);
            });
            info.setAdapter(infoAdapter);
            pager.setAdapter(adapter);

            int height= PresentationUtils.getStatusBarHeight(getResources());
            actionBar.setPadding(0,height,0,0);

            info.addOnScrollListener(listener);
            info.setOnFlingListener(flingListener);
        }
    }


    @OnClick(R.id.share_fab)
    public void reveal(){
        View root=View.inflate(getContext(),R.layout.sheet_movie_details,null);
        final Dialog dialog=new Dialog(getContext(),R.style.DetailsDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(root);
        dialog.setOnShowListener(d->{
            reveal(root,false,dialog);

        });
        dialog.setOnKeyListener((dialog1, keyCode, event) -> {
            if(keyCode==KeyEvent.KEYCODE_BACK){
                reveal(root,true,dialog);
                return true;
            }
            return false;
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void reveal(View dialogView, boolean back, final Dialog dialog) {
        final ViewGroup view = ButterKnife.findById(dialogView,R.id.dialog);
        //make the color darker a bit
        int color=manipulateColor(toggle.getBackgroundTintList().getDefaultColor(),0.8f);
        List<ImageView> buttons=Arrays.asList(
                ButterKnife.findById(view,R.id.add_to_watched),
                ButterKnife.findById(view,R.id.add_to_must_watch),
                ButterKnife.findById(view,R.id.add_to_favorite),
                ButterKnife.findById(view,R.id.share_media),
                ButterKnife.findById(view,R.id.rate_media),
                ButterKnife.findById(view,R.id.review));
        buttons.forEach(button->setDrawableColor(button,color));
        List<View> labels=Arrays.asList(
                ButterKnife.findById(view,R.id.favorite_label),
                ButterKnife.findById(view,R.id.watched_label),
                ButterKnife.findById(view,R.id.must_watch_label),
                ButterKnife.findById(view,R.id.share_media_label),
                ButterKnife.findById(view,R.id.rate_media_label),
                ButterKnife.findById(view,R.id.review_label));
        view.setBackgroundColor(toggle.getBackgroundTintList().getDefaultColor());
        //set the color and make it less opaque
        view.getBackground().setAlpha(220);

        int w = view.getWidth();
        int h = view.getHeight();
        int endRadius = (int) Math.hypot(w, h);
        int cx = (int) (toggle.getX() + (toggle.getWidth()/2));
        int cy = (int) (toggle.getY())+ toggle.getHeight()/4;
        if(!back){
            Animator revealAnimator = ViewAnimationUtils.createCircularReveal(view, cx,cy, 0, endRadius);
            view.setVisibility(View.VISIBLE);
            revealAnimator.setDuration(400);
            revealAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    toggle.setVisibility(View.INVISIBLE);
                    super.onAnimationEnd(animation);
                    AnimatorSet scaleSet=new AnimatorSet();
                    for(int index=0;index<buttons.size();index++){
                        View child=buttons.get(index);
                        View label = labels.get(index);
                        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(label, View.ALPHA, 0, 1);
                        alphaAnimator.setStartDelay(index*50);
                        ObjectAnimator scaleX=ObjectAnimator.ofFloat(child,View.SCALE_X,0,1);
                        ObjectAnimator scaleY=ObjectAnimator.ofFloat(child,View.SCALE_Y,0,1);
                        scaleX.setStartDelay(index*50);
                        scaleY.setStartDelay(index*50);
                        scaleSet.playTogether(scaleX,scaleY,alphaAnimator);
                    }
                    scaleSet.setInterpolator(new OvershootInterpolator(1.2f));
                    scaleSet.setDuration(300);
                    scaleSet.start();

                }
            });
            revealAnimator.start();

        } else {
            Animator anim =ViewAnimationUtils.createCircularReveal(view, cx, cy, endRadius, 0);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    dialog.dismiss();
                    toggle.setVisibility(View.VISIBLE);
                    view.setVisibility(View.INVISIBLE);

                }
            });
            anim.setDuration(400);
            anim.start();
        }
    }

    private int manipulateColor(int color, float factor) {
        int a = Color.alpha(color);
        int r = Math.round(Color.red(color) * factor);
        int g = Math.round(Color.green(color) * factor);
        int b = Math.round(Color.blue(color) * factor);
        return Color.argb(a,
                Math.min(r,255),
                Math.min(g,255),
                Math.min(b,255));
    }

    private void adjustElements(){
        indicatorView.setAnimationType(AnimationType.WORM);
        indicatorView.setTranslationY(pager.getHeight()-indicatorView.getHeight()*2.5f);
        detailsParent.setStaticOffset(pager.getHeight());
        detailsParent.setOffset(pager.getHeight());
        detailsParent.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                View blank=infoAdapter.getBlank();
                ViewGroup.LayoutParams params=blank.getLayoutParams();
                params.height=pager.getHeight() +detailsParent.getHeight();
                blank.setLayoutParams(params);

                int offset=pager.getHeight()+detailsParent.getHeight()-(toggle.getHeight()/2);
                toggle.setStaticOffset(offset);
                toggle.setOffset(offset);
            }
        });
        final float posterOffset=pager.getHeight()-poster.getHeight()*.33f;
        poster.setMinOffset(ViewCompat.getMinimumHeight(pager)+poster.getHeight()/2);
        poster.setStaticOffset(posterOffset);
        poster.setOffset(posterOffset);
        poster.setPinned(true);
        toggle.setMinOffset(ViewCompat.getMinimumHeight(pager)-toggle.getHeight()/2);
    }

    private void shiftElements(){
        ParamsFactory.shiftElementsFrom(poster, Arrays.asList(mediaTitle,releaseYear,tags,mediaDescription));
    }

    private void applyPalette(Palette palette){
        Palette.Swatch swatch=palette.getDarkVibrantSwatch();
        if(swatch==null) swatch=palette.getDominantSwatch();
        //apply if not null
        if(swatch!=null){
            toggle.setBackgroundTintList(ColorStateList.valueOf(swatch.getRgb()));
            detailsParent.setBackgroundColor(swatch.getRgb());
            ChipBuilder builder=tags.getChipBuilder()
                    .setBackgroundColor(swatch.getTitleTextColor())
                    .setTextColor(swatch.getBodyTextColor());
            tags.updateChipColors(builder);
            mediaTitle.setTextColor(swatch.getBodyTextColor());
            releaseYear.setTextColor(swatch.getBodyTextColor());
            mediaRatings.setTextColor(swatch.getBodyTextColor());
            mediaDescription.setTextColor(swatch.getBodyTextColor());
            duration.setTextColor(swatch.getBodyTextColor());
            money.setTextColor(swatch.getBodyTextColor());
            setDrawableColor(money,swatch.getBodyTextColor());
            setDrawableColor(duration,swatch.getBodyTextColor());
            setDrawableColor(releaseYear,swatch.getBodyTextColor());
            setDrawableColor(mediaRatings,swatch.getBodyTextColor());
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

    private void setDrawableColor(ImageView view, int color) {
        Drawable drawable = view.getDrawable();
        if (drawable != null) {
            drawable.mutate();
            DrawableCompat.setTint(drawable, color);
        }
    }

    private RecyclerView.OnFlingListener flingListener = new RecyclerView.OnFlingListener() {
        @Override
        public boolean onFling(int velocityX, int velocityY) {
            poster.setImmediatePin(true);
            pager.setImmediatePin(true);
            return false;
        }
    };

    private RecyclerView.OnScrollListener listener=new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            poster.setImmediatePin(newState==RecyclerView.SCROLL_STATE_SETTLING);
            pager.setImmediatePin(newState == RecyclerView.SCROLL_STATE_SETTLING);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            final int scrollY= infoAdapter.getBlank().getTop();
            pager.setOffset(scrollY);
            poster.setOffset(poster.getStaticOffset()+scrollY);
            detailsParent.setOffset(detailsParent.getStaticOffset()+scrollY);
            toggle.setOffset(toggle.getStaticOffset()+scrollY);

            float min=poster.getOffset()+poster.getHeight();
            float max=detailsParent.getStaticOffset()+detailsParent.getHeight();
            //hide the poster as it goes up
            float alpha=((detailsParent.getOffset()+detailsParent.getHeight())-min)/(max-min);
            poster.setAlpha(alpha);
            toggle.setAlpha(1f-alpha);
            max=pager.getHeight();
            min=indicatorView.getTranslationY()+indicatorView.getHeight();
            //hide the indicator as well
            indicatorView.setAlpha((pager.getOffset()+pager.getHeight()-min)/(max-min));
        }
    };

    @Override
    public void showBackdrops(@NonNull List<String> backdrops) {
        adapter.appendData(backdrops);
    }

    @Override
    public void shareWithMovie(@NonNull MovieDetails details) {

    }

    @Override
    public void showCover(@NonNull MediaCover mediaCover) {
        mediaTitle.setText(mediaCover.getMovieTitle());
        releaseYear.setText(mediaCover.getFormattedDate());
        mediaRatings.setText(mediaCover.getAverageRate());
        duration.setText(mediaCover.getDuration());
        tags.setTags(mediaCover.getGenres());
        if(sharedPosterPath==null){
            sharedPosterPath=mediaCover.getPosterPath();
            loadCover();
        }
    }

    private void loadCover(){
        Glide.with(this)
                .load(sharedPosterPath)
                .priority(Priority.IMMEDIATE)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(poster);
    }

    @Override
    public void showDescription(@NonNull String description) {
        mediaDescription.setText(description);
        detailsParent.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                detailsParent.getViewTreeObserver().removeOnPreDrawListener(this);
                shiftElements();
                getActivity().supportStartPostponedEnterTransition();
                return true;
            }
        });
    }

    @Override
    public void showCollection(@NonNull MediaCollection collection) {
        RelatedMoviesAdapter adapter=new RelatedMoviesAdapter(getContext(),collection.getCovers(),rxBus);
        infoAdapter.addWrapper(InfoAdapter.MovieListWrapper.wrap(adapter,collection.getName()));
    }

    @Override
    public void showSimilar(@NonNull List<MediaCover> covers) {
        RelatedMoviesAdapter adapter=new RelatedMoviesAdapter(getContext(),covers,rxBus);
        infoAdapter.addWrapper(InfoAdapter.MovieListWrapper.wrap(adapter,getString(R.string.media_similar_content)));
    }

    @Override
    public void showSeasons(@NonNull List<SeasonCover> seasons) {
        SeasonAdapter adapter=new SeasonAdapter(getContext(),rxBus);
        adapter.setData(seasons);
        infoAdapter.addWrapper(InfoAdapter.MovieListWrapper.wrap(adapter,getString(R.string.media_seasons)));
    }

    @Override
    public void showTrailers(@NonNull List<Trailer> trailers) {
        MovieTrailersAdapter adapter=new MovieTrailersAdapter(getContext());
        adapter.setData(trailers);
        infoAdapter.addWrapper(InfoAdapter.MovieListWrapper.wrap(adapter,getString(R.string.media_trailers)));
    }

    @Override
    public void showCast(@NonNull List<ActorCover> actorCovers) {
        MovieCastAdapter castAdapter=new MovieCastAdapter(getContext(),rxBus);
        castAdapter.setData(actorCovers);
        infoAdapter.addWrapper(InfoAdapter.MovieListWrapper.wrap(castAdapter,getString(R.string.cast_title)));
    }

    public static class ParamsFactory{

        static void shiftElementsFrom(View target, List<View> shiftElements){
            float posterY=getBottomY(target)+target.getHeight();
            final float posterX=target.getX()+target.getWidth();
            final int offset=target.getWidth()+target.getResources().getDimensionPixelOffset(R.dimen.spacing_medium);
            final float spacing=target.getResources().getDimension(R.dimen.spacing_medium);;
            for(int index=0;index<shiftElements.size();index++){
                View element=shiftElements.get(index);
                if(posterX>=element.getX() && (posterY+spacing)>=getBottomY(element)) {
                    if (index != 0 && shouldShiftVertically(posterY, element,spacing)) {
                        ViewGroup.MarginLayoutParams params = ViewGroup.MarginLayoutParams.class.cast(element.getLayoutParams());
                        final float offsetY = posterY - getBottomY(element) + spacing;
                        params.topMargin += offsetY;
                        posterY -= offsetY;
                        element.setLayoutParams(params);
                    } else {
                        shiftWithMargins(element, offset);
                    }
                }
            }
        }

        static boolean shouldShiftVertically(float offsetY, View target, float spacing){
            final float targetY=getBottomY(target);
            final float offsetDiff=targetY-offsetY;
            return offsetDiff>=0 && offsetDiff<=spacing;
        }

        static boolean shouldShiftHorizontally(float y, View target){
            float targetY=getBottomY(target);
            return y>targetY+target.getHeight()/2;
        }

        static void shiftWithMargins(View target, int offset){
            ViewGroup.MarginLayoutParams params=ViewGroup.MarginLayoutParams.class.cast(target.getLayoutParams());
            params.leftMargin+=offset;
            target.setLayoutParams(params);
        }

        static float getBottomY(View view){
            int[] screenLocation=new int[2];
            view.getLocationOnScreen(screenLocation);
            return screenLocation[1];
        }
    }
}

