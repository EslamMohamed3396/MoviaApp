package com.example.eslam.moviaapp.Activites;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;

import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.eslam.moviaapp.Adapters.ReviewFavAdabterRecycler;
import com.example.eslam.moviaapp.Adapters.TrailerAdapterRecycler;
import com.example.eslam.moviaapp.Adapters.TrailerFavAdapterRecycler;
import com.example.eslam.moviaapp.AppExecutor.AppExecutor;
import com.example.eslam.moviaapp.DataBase.DataBaseMovie;
import com.example.eslam.moviaapp.Models.Movie;
import com.example.eslam.moviaapp.Models.Review;
import com.example.eslam.moviaapp.Models.Trailer;
import com.example.eslam.moviaapp.R;
import com.example.eslam.moviaapp.ViewModel.ReviewFavViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class Favorite extends AppCompatActivity {
    private TextView mTitleText;
    private ImageView mBackImage;
    private ImageView mPosterImage;
    private TextView mOverText;
    private TextView mRateText;
    private TextView mDateText;
    private ImageView mdelete;
    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerViewTrailer;
    private ScrollView mScrollView;

    private String mTitle;
    private String mBackground;
    private String mPoster;
    private String mOverView;
    private String mRate;
    private String mDate;
    private String SID;


    private Toast mToast;
    private Intent intent;
    private DataBaseMovie dataBaseMovie;
    private ReviewFavAdabterRecycler mFavAdabterRecycler;
    private TrailerFavAdapterRecycler mTrailerfavAdapterRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        intent = getIntent();

        bindWidget();

        getAndSetData();


        dataBaseMovie = DataBaseMovie.getINSTANCE(getApplicationContext());


        loadReview();

        loadTrailer();

        mdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delteMovie();
                finish();
                if (mToast != null) {
                    mToast.cancel();
                }
                mToast = Toast.makeText(Favorite.this, getResources().getString(R.string.delete_fav), Toast.LENGTH_SHORT);
                mToast.show();

            }
        });

    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntArray("ARTICLE_SCROLL_POSITION",
                new int[]{mScrollView.getScrollX(), mScrollView.getScrollY()});
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        final int[] position = savedInstanceState.getIntArray("ARTICLE_SCROLL_POSITION");
        if (position != null)
            mScrollView.post(new Runnable() {
                public void run() {
                    mScrollView.scrollTo(position[0], position[1]);
                }
            });

    }

    private void bindWidget() {
        mdelete = findViewById(R.id.im_delete);
        mBackImage = findViewById(R.id.im_background_fav);
        mPosterImage = findViewById(R.id.im_poster_details_fav);
        mOverText = findViewById(R.id.over_fav);
        mRateText = findViewById(R.id.rate_fav);
        mDateText = findViewById(R.id.date_fav);
        mTitleText = findViewById(R.id.title_fav);
        mRecyclerView = findViewById(R.id.rv_review_fav);
        mRecyclerViewTrailer = findViewById(R.id.rv_trailer_fav);
        mScrollView = findViewById(R.id.scroll_fav);
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

    private void loadReview() {
        mFavAdabterRecycler = new ReviewFavAdabterRecycler(this, new ArrayList<Review>());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mFavAdabterRecycler);
        ReviewFavViewModel reviewFavViewModel = ViewModelProviders.of(this).get(ReviewFavViewModel.class);
        reviewFavViewModel.getReview().observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(@Nullable List<Review> reviews) {
                mFavAdabterRecycler.setMovie(reviews);
            }
        });

    }

    private void loadTrailer() {
        mTrailerfavAdapterRecycler = new TrailerFavAdapterRecycler(this, new ArrayList<Trailer>());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);
        mRecyclerViewTrailer.setLayoutManager(gridLayoutManager);
        mRecyclerViewTrailer.setAdapter(mTrailerfavAdapterRecycler);
        dataBaseMovie.movieDao().loadAllTrailer().observe(this, new Observer<List<Trailer>>() {
            @Override
            public void onChanged(@Nullable List<Trailer> trailers) {
                mTrailerfavAdapterRecycler.setMovie(trailers);
            }
        });

    }

    private void delteMovie() {
        mdelete.setImageResource(R.drawable.ic_favorite_border_black_24dp);

        AppExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                dataBaseMovie.movieDao().deleteReviewById(SID);
                dataBaseMovie.movieDao().deleteMovieById(SID);
                dataBaseMovie.movieDao().deleteTrailerById(SID);
            }
        });

    }
}
