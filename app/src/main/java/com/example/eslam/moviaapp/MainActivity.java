package com.example.eslam.moviaapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements movieAdapterRecycler.MovieAdapterOnClick {
    private RecyclerView recyclerView;
    private movieAdapterRecycler adapterRecycler;

    private final static String VOTE_AVERAGE = "vote_average.desc";
    private final static String POPULAR = "popularity.desc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_main);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapterRecycler = new movieAdapterRecycler(MainActivity.this, new ArrayList<Movie>());
        recyclerView.setAdapter(adapterRecycler);
        load__norm_Movie();
    }

    private void load__norm_Movie() {
        new MovieAsync().execute(POPULAR);
    }

    private void load__popular_Movie() {
        adapterRecycler.setMovie(null);
        new MovieAsync().execute(POPULAR);
    }

    private void load__vote_Movie() {
        adapterRecycler.setMovie(null);
        new MovieAsync().execute(VOTE_AVERAGE);
    }

    @Override
    public void onClick(Movie details) {
        Intent intent = new Intent(MainActivity.this, Details_Movie.class);
        intent.putExtra("title", details.getmOriginal_Title());
        intent.putExtra("poster", details.getmMovie_poster());
        intent.putExtra("over", details.getmOverview());
        intent.putExtra("rate", details.getmRating());
        intent.putExtra("date", details.getmRelease_date());
        startActivity(intent);
    }

    public class MovieAsync extends AsyncTask<String, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(String... strings) {
            if (strings.length == 0) {
                return null;
            }

            List<Movie> movieRequestUrl = NetworkUtils.ExtractMovieData(strings[0]);
            return movieRequestUrl;
        }

        @Override
        protected void onPostExecute(List<Movie> movie) {
            if (movie != null && !movie.isEmpty()) {
                adapterRecycler.setMovie(movie);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.popular:
                load__popular_Movie();
                return true;
            case R.id.vote:
                load__vote_Movie();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}