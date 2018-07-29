package com.example.eslam.moviaapp.Loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.eslam.moviaapp.Models.Trailer;
import com.example.eslam.moviaapp.Utils.ExtractData.ExtractDataFromJsonUtils;

import java.util.List;

public class LoadersTrailer extends AsyncTaskLoader<List<Trailer>> {
    private String mUrl;

    public LoadersTrailer(Context context, String mUrl) {
        super(context);
        this.mUrl = mUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Trailer> loadInBackground() {
        return ExtractDataFromJsonUtils.ExtractMovieTrailer(mUrl);
    }
}
