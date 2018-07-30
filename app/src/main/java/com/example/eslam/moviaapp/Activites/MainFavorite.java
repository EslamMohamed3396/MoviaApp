package com.example.eslam.moviaapp.Activites;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.eslam.moviaapp.Adapters.MovieFavAdapterRecycler;
import com.example.eslam.moviaapp.Adapters.movieAdapterRecycler;
import com.example.eslam.moviaapp.DataBase.DataBaseMovie;
import com.example.eslam.moviaapp.Models.Movie;
import com.example.eslam.moviaapp.R;

import java.util.ArrayList;
import java.util.List;

public class MainFavorite extends AppCompatActivity implements MovieFavAdapterRecycler.MovieAdapterOnClick {
    private DataBaseMovie dataBaseMovie;
    private RecyclerView recyclerView;
    private MovieFavAdapterRecycler adapterRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_favorite);

        dataBaseMovie = DataBaseMovie.getINSTANCE(getApplicationContext());

        recyclerView = findViewById(R.id.recycler_main_fav);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapterRecycler = new MovieFavAdapterRecycler(this, new ArrayList<Movie>());
        recyclerView.setAdapter(adapterRecycler);

        retriveData();
    }

    private void retriveData() {
        LiveData<List<Movie>> loadAllMovie = dataBaseMovie.movieDao().loadAllMovie();
        loadAllMovie.observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                if (movies != null && !movies.isEmpty()) {
                    adapterRecycler.setMovie(movies);
                } else {
                    Toast.makeText(MainFavorite.this, "You Don't have Favorite Movie Yet", Toast.LENGTH_LONG).show();
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
