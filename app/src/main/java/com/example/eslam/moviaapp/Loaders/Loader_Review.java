package com.example.eslam.moviaapp.Loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.eslam.moviaapp.Models.Review;
import com.example.eslam.moviaapp.Utils.ExtractData.ExtractDataFromJsonUtils;

import java.util.List;

public class Loader_Review extends AsyncTaskLoader<List<Review>> {
    private String mUrl;

    public Loader_Review(Context context, String mUrl) {
        super(context);
        this.mUrl = mUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Review> loadInBackground() {
        return ExtractDataFromJsonUtils.ExtractMovieReview(mUrl);
    }
}
