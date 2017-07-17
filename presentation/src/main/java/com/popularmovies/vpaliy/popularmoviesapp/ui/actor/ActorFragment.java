package com.popularmovies.vpaliy.popularmoviesapp.ui.actor;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.popularmovies.vpaliy.domain.model.ActorDetails;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import com.popularmovies.vpaliy.popularmoviesapp.App;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerViewComponent;
import com.popularmovies.vpaliy.popularmoviesapp.di.module.PresenterModule;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseFragment;
import com.popularmovies.vpaliy.popularmoviesapp.ui.details.adapter.RelatedMoviesAdapter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Constants;
import java.util.List;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

    @BindView(R.id.actor_media_credits)
    protected RecyclerView actorCredits;

    @BindView(R.id.actor_name)
    protected TextView actorName;

    @BindView(R.id.guideline)
    protected View guideline;

    private BiographyAdapter biographyAdapter;

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
            biographyAdapter=new BiographyAdapter(getContext());
            actorCredits.setAdapter(biographyAdapter);
            actorCredits.addOnScrollListener(scrollListener);
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
                .into(actorBackdrop);
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
        biographyAdapter.addInfo(entity.buildActorInfo());
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

    @Override
    public void showMovieCredits(@NonNull List<MediaCover> movies) {
        RelatedMoviesAdapter adapter=new RelatedMoviesAdapter(getContext(),movies,rxBus);
        biographyAdapter.addWrapper(BiographyAdapter.MovieListWrapper.wrap(adapter,getString(R.string.movies)));
    }

    @Override
    public void showTvShowCredits(@NonNull List<MediaCover> tvShows) {
        RelatedMoviesAdapter adapter=new RelatedMoviesAdapter(getContext(),tvShows,rxBus);
        biographyAdapter.addWrapper(BiographyAdapter.MovieListWrapper.wrap(adapter,getString(R.string.tv_shows)));
    }

    private RecyclerView.OnScrollListener scrollListener=new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            final float translationY=diagonalLayout.getTranslationY()-dy;
            guideline.setTranslationY(translationY);
            diagonalLayout.setTranslationY(translationY);
            actorImage.setTranslationY(translationY);
            actorName.setTranslationY(translationY);
        }
    };
}
