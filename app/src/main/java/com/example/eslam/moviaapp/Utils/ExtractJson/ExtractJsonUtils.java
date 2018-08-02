package com.example.eslam.moviaapp.Utils.ExtractJson;

import android.text.TextUtils;

import com.example.eslam.moviaapp.Models.Movie;
import com.example.eslam.moviaapp.Models.Review;
import com.example.eslam.moviaapp.Models.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class ExtractJsonUtils {
    private final static String ID = "id";
    private final static String RESULT = "results";
    private final static String TITLE = "original_title";
    private final static String OVER_VIEW = "overview";
    private final static String POSTER = "poster_path";
    private final static String BACKGROUND = "backdrop_path";
    private final static String VOTE = "vote_average";
    private final static String DATE = "release_date";
    private final static String IMAGE_URL_COMPELTE = "https://image.tmdb.org/t/p/original";


    private final static String KEY_YOUTUBE = "key";

    private final static String AUTHOR = "author";
    private final static String CONTENT = "content";


    public static List<Trailer> extractFeatureFromJsonTrailer(String Stringjson) {
        if (TextUtils.isEmpty(Stringjson)) {
            return null;
        }
        List<Trailer> trailers = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(Stringjson);
            String id = root.getString(ID);
            JSONArray result = root.getJSONArray(RESULT);
            for (int i = 0; i < result.length(); i++) {
                JSONObject currentMovie = result.getJSONObject(i);
                String trailer = currentMovie.getString(KEY_YOUTUBE);
                trailers.add(new Trailer(id, trailer));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return trailers;
    }


    public static List<Review> extractFeatureFromJsonReview(String Stringjson) {
        if (TextUtils.isEmpty(Stringjson)) {
            return null;
        }
        List<Review> reviews = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(Stringjson);
            String id = root.getString(ID);
            JSONArray result = root.getJSONArray(RESULT);
            if (result != null && result.length() > 0) {
                for (int i = 0; i < result.length(); i++) {
                    JSONObject currentMovie = result.getJSONObject(i);
                    String author = currentMovie.getString(AUTHOR);
                    String content = currentMovie.getString(CONTENT);
                    reviews.add(new Review(id, author, content));
                }
            } else {
                reviews.add(new Review());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reviews;
    }


    public static List<Movie> extractFeatureFromJson(String Stringjson) {
        if (TextUtils.isEmpty(Stringjson)) {
            return null;
        }
        List<Movie> movieList = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(Stringjson);
            JSONArray result = root.getJSONArray(RESULT);
            for (int i = 0; i < result.length(); i++) {
                JSONObject currentMovie = result.getJSONObject(i);
                String id = currentMovie.getString(ID);
                String title = currentMovie.getString(TITLE);
                String overview = currentMovie.getString(OVER_VIEW);
                String poster = currentMovie.getString(POSTER);
                String backGround = currentMovie.getString(BACKGROUND);
                String rate = currentMovie.getString(VOTE);
                String date = currentMovie.getString(DATE);
                String Image_Poster_Concat = IMAGE_URL_COMPELTE.concat(poster);
                String backGround_Concat = IMAGE_URL_COMPELTE.concat(backGround);
                movieList.add(new Movie(id, title, Image_Poster_Concat, overview, rate, date, backGround_Concat));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieList;
    }

}
