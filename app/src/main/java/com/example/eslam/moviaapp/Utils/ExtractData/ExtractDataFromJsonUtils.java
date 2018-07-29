package com.example.eslam.moviaapp.Utils.ExtractData;


import com.example.eslam.moviaapp.Models.Movie;
import com.example.eslam.moviaapp.Models.Review;
import com.example.eslam.moviaapp.Models.Trailer;
import com.example.eslam.moviaapp.Utils.ExtractJson.ExtractJsonUtils;
import com.example.eslam.moviaapp.Utils.NetworkConnection.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public final class ExtractDataFromJsonUtils {

    public static List<Movie> ExtractMovieData(String URL) {
        String jsonResponse = null;
        URL url = NetworkUtils.createURL(URL);
        try {
            jsonResponse = NetworkUtils.getResponseFromHttpUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Movie> movieDetails = ExtractJsonUtils.extractFeatureFromJson(jsonResponse);
        return movieDetails;
    }

    public static List<Trailer> ExtractMovieTrailer(String murl) {
        String jsonResponse = null;
        URL url = NetworkUtils.createURL_Trailer(murl);
        try {
            jsonResponse = NetworkUtils.getResponseFromHttpUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Trailer> trailers = ExtractJsonUtils.extractFeatureFromJsonTrailer(jsonResponse);
        return trailers;
    }

    public static List<Review> ExtractMovieReview(String murl) {
        String jsonResponse = null;
        URL url = NetworkUtils.createURL_Trailer(murl);
        try {
            jsonResponse = NetworkUtils.getResponseFromHttpUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Review> movieDetails = ExtractJsonUtils.extractFeatureFromJsonReview(jsonResponse);
        return movieDetails;
    }

}
