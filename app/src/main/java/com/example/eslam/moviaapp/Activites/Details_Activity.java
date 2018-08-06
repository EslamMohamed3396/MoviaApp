package com.example.eslam.moviaapp.Activites;

import android.app.LoaderManager;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;

import com.example.eslam.moviaapp.Adapters.ReviewAdapterRecycler;
import com.example.eslam.moviaapp.Adapters.TrailerAdapterRecycler;
import com.example.eslam.moviaapp.AppExecutor.AppExecutor;
import com.example.eslam.moviaapp.DataBase.DataBaseMovie;
import com.example.eslam.moviaapp.Loaders.Loader_Review;
import com.example.eslam.moviaapp.Loaders.LoadersTrailer;
import com.example.eslam.moviaapp.Models.Movie;
import com.example.eslam.moviaapp.Models.Review;
import com.example.eslam.moviaapp.Models.Trailer;
import com.example.eslam.moviaapp.R;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class Details_Activity extends AppCompatActivity {

    private RecyclerView mRecyclerView_Trailer;
    private RecyclerView mRecyclerView_Review;
    private TextView mTitleText;
    private ImageView mBackImage;
    private ImageView mPosterImage;
    private TextView mOverText;
    private TextView mRateText;
    private TextView mDateText;
    private ImageView mSave;
    private ScrollView mScrollView;

    private String mTitle;
    private String mBackground;
    private String mPoster;
    private String mOverView;
    private String mRate;
    private String mDate;

    private static String SID;
    private static final int TRAILER_ID = 1;
    private static final int REVIEW_ID = 2;
    private static final String PATH_TRAILER = "/videos";
    private static final String PATH_REVIEW = "/reviews";
    private static final String KEY_SCROLL = "ARTICLE_SCROLL_POSITION";
    private TrailerAdapterRecycler trailerAdapterRecycler;
    private ReviewAdapterRecycler reviewAdapterRecycler;
    private DataBaseMovie dataBaseMovie;
    private Intent intent;
    private boolean isFavorite;
    private Toast mToast;
    LoaderManager.LoaderCallbacks<List<Trailer>> mTrailerCallbacks = new LoaderManager.LoaderCallbacks<List<Trailer>>() {
        @Override
        public Loader<List<Trailer>> onCreateLoader(int i, Bundle bundle) {
            String BASE_URL_MOVIE_Trailer = "https://api.themoviedb.org/3/movie/" + SID + "" + PATH_TRAILER + "";
            return new LoadersTrailer(Details_Activity.this, BASE_URL_MOVIE_Trailer);
        }

        @Override
        public void onLoadFinished(Loader<List<Trailer>> loader, List<Trailer> trailers) {
            trailerAdapterRecycler.setMovie(trailers);
        }

        @Override
        public void onLoaderReset(Loader<List<Trailer>> loader) {
            trailerAdapterRecycler.setMovie(null);
        }
    };

    LoaderManager.LoaderCallbacks<List<Review>> mReviewCallbacks = new LoaderManager.LoaderCallbacks<List<Review>>() {
        @Override
        public Loader<List<Review>> onCreateLoader(int i, Bundle bundle) {
            String BASE_URL_MOVIE_Trailer = "https://api.themoviedb.org/3/movie/" + SID + "" + PATH_REVIEW + "";
            return new Loader_Review(Details_Activity.this, BASE_URL_MOVIE_Trailer);
        }

        @Override
        public void onLoadFinished(Loader<List<Review>> loader, List<Review> reviews) {
            if (reviews != null && !reviews.isEmpty()) {

                reviewAdapterRecycler.setMovie(reviews);
            }

        }

        @Override
        public void onLoaderReset(Loader<List<Review>> loader) {
            reviewAdapterRecycler.setMovie(null);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details__movie);
        bindWidget();

        dataBaseMovie = DataBaseMovie.getINSTANCE(getApplicationContext());

        getAndSetData();

        loadTrailer();
        loadReview();
        isFav();

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMovie();

            }
        });

    }

    private void isFav() {
        LiveData<List<Movie>> loadAllMovieByID = dataBaseMovie.movieDao().loadAllMovieByID(SID);
        loadAllMovieByID.observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                if (movies.isEmpty()) {
                    isFavorite = true;
                    mSave.setImageResource(R.drawable.ic_favorite_border_black_24dp);

                } else {
                    isFavorite = false;
                    mSave.setImageResource(R.drawable.ic_favorite_black_24dp);
                }
            }
        });

    }

    private void getAndSetData() {
        intent = getIntent();

        SID = intent.getStringExtra("id");
        mTitle = intent.getStringExtra("title");
        mBackground = intent.getStringExtra("back");
        mPoster = intent.getStringExtra("poster");
        Picasso.with(this).load(mBackground).into(mBackImage);
        Picasso.with(this).load(mPoster).into(mPosterImage);
        mOverView = intent.getStringExtra("over");
        mRate = intent.getStringExtra("rate");
        mDate = intent.getStringExtra("date");

        mTitleText.setText(mTitle);
        mOverText.setText(mOverView);
        mRateText.setText(mRate);
        mDateText.setText(mDate);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntArray(KEY_SCROLL,
                new int[]{mScrollView.getScrollX(), mScrollView.getScrollY()});
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        final int[] position = savedInstanceState.getIntArray(KEY_SCROLL);
        if (position != null)
            mScrollView.post(new Runnable() {
                public void run() {
                    mScrollView.scrollTo(position[0], position[1]);
                }
            });

    }

    private void addMovie() {
        if (isFavorite) {
            final Movie movie = new Movie(SID, mTitle, mPoster, mOverView, mRate, mDate, mBackground);
            AppExecutor.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    dataBaseMovie.movieDao().insertMovie(movie);
                }
            });
            addReviewAndTrailer();
            mSave.setImageResource(R.drawable.ic_favorite_black_24dp);

            if (mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(this, getResources().getString(R.string.add_fav), Toast.LENGTH_SHORT);
            mToast.show();
        } else {
            if (mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(this, getResources().getString(R.string.added_before), Toast.LENGTH_SHORT);
            mToast.show();
        }
    }

    private void addReviewAndTrailer() {
        final List<Review> review = reviewAdapterRecycler.getData();
        AppExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                dataBaseMovie.movieDao().insertReview(review);
            }
        });

        final List<Trailer> trailers = trailerAdapterRecycler.getData();
        AppExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                dataBaseMovie.movieDao().insertTrailer(trailers);
            }
        });
    }


    private void loadTrailer() {
        trailerAdapterRecycler = new TrailerAdapterRecycler(this, new ArrayList<Trailer>());
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);
        mRecyclerView_Trailer.setLayoutManager(layoutManager);
        mRecyclerView_Trailer.setAdapter(trailerAdapterRecycler);

        LoaderManager managerTrailer = getLoaderManager();
        managerTrailer.initLoader(TRAILER_ID, null, mTrailerCallbacks);
    }

    private void loadReview() {
        reviewAdapterRecycler = new ReviewAdapterRecycler(this, new ArrayList<Review>());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        mRecyclerView_Review.setLayoutManager(gridLayoutManager);
        mRecyclerView_Review.setAdapter(reviewAdapterRecycler);
        LoaderManager managerReview = getLoaderManager();
        managerReview.initLoader(REVIEW_ID, null, mReviewCallbacks);

    }

    private void bindWidget() {
        mSave = findViewById(R.id.im_save);
        mScrollView = findViewById(R.id.scroll_view);
        mBackImage = findViewById(R.id.im_background);
        mPosterImage = findViewById(R.id.im_poster_details);
        mOverText = findViewById(R.id.over);
        mRateText = findViewById(R.id.rate);
        mDateText = findViewById(R.id.date);
        mTitleText = findViewById(R.id.title);
        mRecyclerView_Trailer = findViewById(R.id.rv_trailer);
        mRecyclerView_Review = findViewById(R.id.rv_review);
    }

}