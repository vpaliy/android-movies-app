package com.popularmovies.vpaliy.popularmoviesapp.ui.details;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import butterknife.ButterKnife;
import io.codetail.animation.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.popularmovies.vpaliy.domain.configuration.SortType;
import com.popularmovies.vpaliy.domain.model.ActorCover;
import com.popularmovies.vpaliy.domain.model.MediaCollection;
import com.popularmovies.vpaliy.domain.model.MediaCover;
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

//TODO change opacity
//TODO back transition
//TODO fade button
//TODO change color
//TODO remove responsibilities
//TODO transition to similar movies
//TODO transition to actors
//TODO transition to more
//TODO transition from media to details
//TODO remove lambda
//TODO fix movie fetching and tv shows as well

import static com.popularmovies.vpaliy.popularmoviesapp.ui.details.MediaDetailsContract.Presenter;
import static com.popularmovies.vpaliy.popularmoviesapp.ui.utils.ColorUtils.dimColor;
import static com.popularmovies.vpaliy.popularmoviesapp.ui.utils.ColorUtils.setDrawableColor;

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

    @BindView(R.id.dialogParent)
    protected ViewGroup dialog;

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
    public void showDuration(@NonNull String duration) {
        this.duration.setVisibility(View.VISIBLE);
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
            dialog.post(()->dialog.setVisibility(View.GONE));
            getActivity().supportPostponeEnterTransition();
            infoAdapter=new InfoAdapter(getContext());
            adapter=new MovieBackdropsAdapter(getContext());
            adapter.setData(Collections.singletonList(sharedPath));
            adapter.setCallback((image,bitmap)->{
                indicatorView.setAnimationType(AnimationType.THIN_WORM);
                indicatorView.setTranslationY(image.getHeight()-indicatorView.getHeight()*2.5f);
                detailsParent.setStaticOffset(image.getHeight());
                detailsParent.setOffset(image.getHeight());
                final float posterOffset=image.getHeight()-poster.getHeight()*.33f;
                poster.setMinOffset(ViewCompat.getMinimumHeight(image)+poster.getHeight()/2);
                poster.setStaticOffset(posterOffset);
                poster.setOffset(posterOffset);
                poster.setPinned(true);
                toggle.setMinOffset(ViewCompat.getMinimumHeight(pager)-toggle.getHeight()/2);
                new Palette.Builder(bitmap).generate(MediaDetailsFragment.this::applyPalette);
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
        if(toggle.getAlpha()>0.1f) {
            dialog.setVisibility(View.VISIBLE);
            reveal(dialog, false);
        }
    }

    public boolean isDialogOn(){
        return dialog.getVisibility()==View.VISIBLE;
    }

    private void reveal(View dialogView, boolean back) {
            ViewCompat.setTranslationZ(pager, 0);
            final ViewGroup view = ButterKnife.findById(dialogView, R.id.dialog);
            //conceal when is touched
            view.setOnClickListener(v -> reveal(dialogView, true));
            //make the color darker a bit
            int color = dimColor(toggle.getBackgroundTintList().getDefaultColor(), 0.8f);
            List<ImageView> buttons = Arrays.asList(
                    ButterKnife.findById(view, R.id.add_to_watched),
                    ButterKnife.findById(view, R.id.add_to_must_watch),
                    ButterKnife.findById(view, R.id.add_to_favorite),
                    ButterKnife.findById(view, R.id.share_media),
                    ButterKnife.findById(view, R.id.rate_media),
                    ButterKnife.findById(view, R.id.review));
            buttons.forEach(button -> setDrawableColor(button, color));
            List<View> labels = Arrays.asList(
                    ButterKnife.findById(view, R.id.favorite_label),
                    ButterKnife.findById(view, R.id.watched_label),
                    ButterKnife.findById(view, R.id.must_watch_label),
                    ButterKnife.findById(view, R.id.share_media_label),
                    ButterKnife.findById(view, R.id.rate_media_label),
                    ButterKnife.findById(view, R.id.review_label));
            view.setBackgroundColor(toggle.getBackgroundTintList().getDefaultColor());
            //set the color and make it less opaque
           // view.getBackground().setAlpha(210);
            int w = view.getWidth();
            int h = view.getHeight();
            int endRadius = (int) Math.hypot(w, h);
            int cx = (int) (toggle.getX() + (toggle.getWidth() / 2));
            int cy = (int) (toggle.getY()) + toggle.getHeight() / 4;
            if (!back) {
                toggle.setVisibility(View.INVISIBLE);
                Animator revealAnimator = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, endRadius);
                view.setVisibility(View.VISIBLE);
                revealAnimator.setDuration(300);
                revealAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        AnimatorSet scaleSet = new AnimatorSet();
                        for (int index = 0; index < buttons.size(); index++) {
                            View child = buttons.get(index);
                            View label = labels.get(index);
                            child.setVisibility(View.VISIBLE);
                            ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(label, View.ALPHA, 0, 1);
                            alphaAnimator.setStartDelay(index * 50);
                            ObjectAnimator scaleX = ObjectAnimator.ofFloat(child, View.SCALE_X, 0, 1);
                            ObjectAnimator scaleY = ObjectAnimator.ofFloat(child, View.SCALE_Y, 0, 1);
                            scaleX.setStartDelay(index * 50);
                            scaleY.setStartDelay(index * 50);
                            scaleSet.playTogether(scaleX, scaleY, alphaAnimator);
                        }
                        scaleSet.setInterpolator(new OvershootInterpolator(1.2f));
                        scaleSet.setDuration(300);
                        scaleSet.start();

                    }
                });
                revealAnimator.start();

            } else {
                Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, endRadius, 0);
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        for (int index = 0; index < buttons.size(); index++) {
                            View child = buttons.get(index);
                            View label = labels.get(index);
                            child.setScaleY(0);
                            child.setScaleX(0);
                            label.setAlpha(0);
                            label.setAlpha(0);
                            child.setVisibility(View.INVISIBLE);
                        }
                        dialogView.setVisibility(View.GONE);
                        toggle.setVisibility(View.VISIBLE);
                        view.setVisibility(View.GONE);

                    }
                });
                anim.setDuration(300);
                anim.start();
            }
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

    public void turnOffDialog(){
        reveal(dialog,true);
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
    public void share(@NonNull String shareText) {
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT,shareText);
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent,getString(R.string.chooseToWhomToSend)));
    }

    @Override
    public void showCover(@NonNull MediaCover mediaCover) {
        mediaTitle.setText(mediaCover.getMovieTitle());
        releaseYear.setText(mediaCover.getFormattedDate());
        mediaRatings.setText(mediaCover.getAverageRate());
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
                ParamsFactory.shiftElementsFrom(poster, Arrays.asList(mediaTitle,releaseYear,tags,mediaDescription));
                detailsParent.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                    @Override
                    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                        View blank=infoAdapter.getBlank();
                        ViewGroup.LayoutParams params=blank.getLayoutParams();
                        params.height=pager.getHeight()+detailsParent.getHeight();
                        blank.setLayoutParams(params);
                        int offset = pager.getHeight() + detailsParent.getHeight() - (toggle.getHeight() / 2);
                        toggle.setStaticOffset(offset);
                        toggle.setOffset(offset);
                        detailsParent.removeOnLayoutChangeListener(this);
                    }
                });
                getActivity().supportStartPostponedEnterTransition();
                return true;
            }
        });
    }

    @OnClick(value = {R.id.add_to_favorite, R.id.add_to_must_watch, R.id.add_to_watched})
    public void addToList(View button) {
        switch (button.getId()) {
            case R.id.add_to_watched:
                presenter.make(SortType.WATCHED);
                break;
            case R.id.add_to_favorite:
                presenter.make(SortType.FAVORITE);
                break;
            case R.id.add_to_must_watch:
                presenter.make(SortType.MUST_WATCH);
                break;
        }
    }

    @OnClick(value ={R.id.share_media,R.id.rate_media,R.id.review})
    public void applyAction(View action){
        switch (action.getId()){
            case R.id.share_media:
                presenter.share();
                break;
        }
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

    private static class ParamsFactory{

        static void shiftElementsFrom(View target, List<View> shiftElements){
            float posterY=getBottomY(target)+target.getHeight();
            final float posterX=target.getX()+target.getWidth();
            final float spacing=target.getResources().getDimension(R.dimen.spacing_media_details);
            final float offset=target.getWidth()+spacing;
            for(int index=0;index<shiftElements.size();index++){
                View element=shiftElements.get(index);
                if(posterX>=element.getX() && (posterY+spacing)>=getBottomY(element)) {
                    if (index != 0 && shouldShiftVertically(posterY, element,spacing)) {
                        posterY=shiftVertically(element,posterY);
                    } else {
                        shiftHorizontally(element, offset);
                    }
                }
            }
        }

        static boolean shouldShiftVertically(float offsetY, View target, float spacing){
            final float targetY=getBottomY(target);
            final float offsetDiff=targetY-offsetY;
            return offsetDiff>=0 && offsetDiff<=spacing;
        }

        static float shiftVertically(View target, float posterY){
            final float spacing=target.getResources().getDimension(R.dimen.spacing_media_details);
            ViewGroup.MarginLayoutParams params = ViewGroup.MarginLayoutParams.class.cast(target.getLayoutParams());
            final float offsetY = posterY - getBottomY(target) + spacing;
            params.topMargin += offsetY;
            posterY -= offsetY;
            target.setLayoutParams(params);
            return posterY;
        }

        static void shiftHorizontally(View target, float offset){
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

