package com.example.eslam.moviaapp.Loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.eslam.moviaapp.Models.Movie;
import com.example.eslam.moviaapp.Utils.ExtractData.ExtractDataFromJsonUtils;

import java.util.List;

public class LoadersMovie extends AsyncTaskLoader<List<Movie>> {
    private String mUrl;

    public LoadersMovie(Context context, String mUrl) {
        super(context);
        this.mUrl = mUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Movie> loadInBackground() {
        List<Movie> movieRequestUrl = ExtractDataFromJsonUtils.ExtractMovieData(mUrl);
        return movieRequestUrl;
    }
}
