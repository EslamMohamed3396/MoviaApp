package com.example.eslam.moviaapp.Activites;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.example.eslam.moviaapp.Adapters.MovieFavAdapterRecycler;
import com.example.eslam.moviaapp.Models.Movie;
import com.example.eslam.moviaapp.Models.Review;
import com.example.eslam.moviaapp.R;
import com.example.eslam.moviaapp.ViewModel.MainFavViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainFavorite extends AppCompatActivity implements MovieFavAdapterRecycler.MovieAdapterOnClick {
    private RecyclerView recyclerView;
    private MovieFavAdapterRecycler adapterRecycler;
    private int mSpanCount;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_favorite);

        mSpanCount = calculateNoOfColumns(this);
        recyclerView = findViewById(R.id.recycler_main_fav);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, mSpanCount, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapterRecycler = new MovieFavAdapterRecycler(this, new ArrayList<Movie>());
        recyclerView.setAdapter(adapterRecycler);

        retriveData();
    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 200;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        if (noOfColumns < 2)
            noOfColumns = 2;
        return noOfColumns;
    }

    private void retriveData() {
        MainFavViewModel mainFavViewModel = ViewModelProviders.of(this).get(MainFavViewModel.class);
        mainFavViewModel.getMovie().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                if (movies != null && !movies.isEmpty()) {
                    adapterRecycler.setMovie(movies);
                } else {
                    if (mToast != null) {
                        mToast.cancel();
                    }
                    mToast = Toast.makeText(MainFavorite.this, "You Don't have Favorite Movie Yet", Toast.LENGTH_LONG);
                    mToast.show();

                }
            }
        });
    }

    @Override
    public void onClick(Movie details) {
        Intent intent = new Intent(MainFavorite.this, Favorite.class);
        intent.putExtra("id", details.getmId());
        intent.putExtra("poster", details.getmMovie_poster());
        intent.putExtra("title", details.getmOriginal_Title());
        intent.putExtra("back", details.getmBackGround());
        intent.putExtra("over", details.getmOverview());
        intent.putExtra("rate", details.getmRating());
        intent.putExtra("date", details.getmRelease_date());
        startActivity(intent);
    }
}
