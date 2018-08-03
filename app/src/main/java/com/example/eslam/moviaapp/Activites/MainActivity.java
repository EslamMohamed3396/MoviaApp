package com.example.eslam.moviaapp.Activites;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;

import com.example.eslam.moviaapp.Loaders.LoadersMovie;
import com.example.eslam.moviaapp.R;

import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.eslam.moviaapp.Adapters.movieAdapterRecycler;
import com.example.eslam.moviaapp.Models.Movie;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements movieAdapterRecycler.MovieAdapterOnClick {

    private RecyclerView recyclerView;
    private movieAdapterRecycler adapterRecycler;
    private int mSpanCount;
    private final static String VOTE_AVERAGE = "top_rated";
    private final static String POPULAR = "popular";
    private final static int LOADERVOTE_AVERAGE = 1;
    private final static int LOADERPOPULAR = 2;
    private GridLayoutManager mLayoutManager;
    private final static String MENU_SELECTED = "selected";
    private int mSelected = -1;
    private MenuItem mMenuItem;
    FavoriteFragment mFavFragment;
    private LoaderManager.LoaderCallbacks<List<Movie>> mLoaderCallbacksPOPULAR = new LoaderManager.LoaderCallbacks<List<Movie>>() {
        @Override
        public Loader<List<Movie>> onCreateLoader(int i, Bundle bundle) {
            return new LoadersMovie(MainActivity.this, POPULAR);
        }

        @Override
        public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movies) {
            adapterRecycler.setMovie(movies);
        }

        @Override
        public void onLoaderReset(Loader<List<Movie>> loader) {
            adapterRecycler.setMovie(null);

        }
    };
    private LoaderManager.LoaderCallbacks<List<Movie>> mLoaderCallbacksVOTE_AVERAGE = new LoaderManager.LoaderCallbacks<List<Movie>>() {
        @Override
        public Loader<List<Movie>> onCreateLoader(int i, Bundle bundle) {
            return new LoadersMovie(MainActivity.this, VOTE_AVERAGE);
        }

        @Override
        public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movies) {
            adapterRecycler.setMovie(movies);
        }

        @Override
        public void onLoaderReset(Loader<List<Movie>> loader) {
            adapterRecycler.setMovie(null);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_main);

        mSpanCount = calculateNoOfColumns(this);

        recyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this, mSpanCount, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        adapterRecycler = new movieAdapterRecycler(MainActivity.this, new ArrayList<Movie>());
        recyclerView.setAdapter(adapterRecycler);
        load_Popular_Movie();

    }

    private void bindFragment() {
        mFavFragment = new FavoriteFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.frame_content, mFavFragment)
                .commit();
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

    private boolean check_The_InterNet() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        boolean isConncted = networkInfo != null &&
                networkInfo.isConnectedOrConnecting();
        if (isConncted) {
            return true;
        }
        Toast.makeText(this, "Please Open You InterNet", Toast.LENGTH_SHORT).show();
        return false;
    }

    private void load_Popular_Movie() {
        if (check_The_InterNet()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(LOADERPOPULAR, null, mLoaderCallbacksPOPULAR);
        }
    }

    private void load_High_Rated_Movie() {
        if (check_The_InterNet()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(LOADERVOTE_AVERAGE, null, mLoaderCallbacksVOTE_AVERAGE);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(MENU_SELECTED, mSelected);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            mSelected = savedInstanceState.getInt(MENU_SELECTED);
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
    private void removeFragment()
    {
        getSupportFragmentManager().beginTransaction().remove(mFavFragment).commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        if (mSelected == -1) {
            return true;
        }

        switch (mSelected) {
            case R.id.popular:
                mMenuItem = menu.findItem(R.id.popular);
                load_Popular_Movie();
                break;
            case R.id.vote:
                mMenuItem = menu.findItem(R.id.vote);
                load_High_Rated_Movie();
                break;
            case R.id.fav:
                mMenuItem = menu.findItem(R.id.fav);
                bindFragment();
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.popular:
                mSelected = id;
                removeFragment();
                load_Popular_Movie();
                return true;
            case R.id.vote:
                mSelected = id;
                removeFragment();
                load_High_Rated_Movie();
                return true;
            case R.id.fav:
                mSelected = id;
                bindFragment();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}