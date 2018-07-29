package com.example.eslam.moviaapp.Activites;


import android.content.Intent;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.eslam.moviaapp.AppExecutor.AppExecutor;
import com.example.eslam.moviaapp.DataBase.DataBaseMovie;
import com.example.eslam.moviaapp.Models.Movie;
import com.example.eslam.moviaapp.R;
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

    private String mTitle;
    private String mBackground;
    private String mPoster;
    private String mOverView;
    private String mRate;
    private String mDate;
    private static String SID;


    private Intent intent;
    private DataBaseMovie dataBaseMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        intent = getIntent();
        bindWidget();
        getAndSetData();
        dataBaseMovie = DataBaseMovie.getINSTANCE(getApplicationContext());
        mdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delteMovie();
                Toast.makeText(Favorite.this,getResources().getString(R.string.delete_fav), Toast.LENGTH_SHORT).show();

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

    private void delteMovie() {
        mdelete.setImageResource(R.drawable.ic_favorite_border_black_24dp);

        AppExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                dataBaseMovie.movieDao().deleteMovieById(SID);
            }
        });

    }
}
