package com.example.eslam.moviaapp.Activites;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.eslam.moviaapp.R;

import android.widget.Toast;

import com.example.eslam.moviaapp.Adapters.movieAdapterRecycler;
import com.example.eslam.moviaapp.Models.Movie;
import com.example.eslam.moviaapp.Utils.ExtractData.ExtractDataFromJsonUtils;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements movieAdapterRecycler.MovieAdapterOnClick {
    private RecyclerView recyclerView;
    private movieAdapterRecycler adapterRecycler;

    private final static String VOTE_AVERAGE = "top_rated";
    private final static String POPULAR = "popular";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_main);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapterRecycler = new movieAdapterRecycler(MainActivity.this, new ArrayList<Movie>());
        recyclerView.setAdapter(adapterRecycler);
        load_Movies();
    }

    private boolean check_The_InterNet() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        boolean isConncted = networkInfo != null &&
                networkInfo.isConnectedOrConnecting();
        if (isConncted) {
            return true;
        }
        Toast.makeText(this, "Please Open You InterNet", Toast.LENGTH_SHORT).show();
        return false;
    }

    private void load_Movies() {
        if (check_The_InterNet()) {
            new MovieAsync().execute(POPULAR);
        }
    }

    private void load_Popular_Movie() {
        if (check_The_InterNet()) {
            adapterRecycler.setMovie(null);
            new MovieAsync().execute(POPULAR);
        }
    }

    private void load_High_Rated_Movie() {
        if (check_The_InterNet()) {
            adapterRecycler.setMovie(null);
            new MovieAsync().execute(VOTE_AVERAGE);
        }
    }

    @Override
    public void onClick(Movie details) {
        Intent intent = new Intent(MainActivity.this, Details_Activity.class);
        intent.putExtra("id", details.getmId());
        intent.putExtra("poster", details.getmMovie_poster());
        intent.putExtra("title", details.getmOriginal_Title());
        intent.putExtra("back", details.getmBackGround());
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

            List<Movie> movieRequestUrl = ExtractDataFromJsonUtils.ExtractMovieData(strings[0]);
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
                load_Popular_Movie();
                return true;
            case R.id.vote:
                load_High_Rated_Movie();
                return true;
            case R.id.fav:
                startActivity(new Intent(this, MainFavorite.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}