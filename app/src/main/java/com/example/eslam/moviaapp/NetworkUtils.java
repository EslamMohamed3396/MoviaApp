package com.example.eslam.moviaapp;

import android.net.Uri;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class NetworkUtils {
    private final static String BASE_URL_MOVIE = "https://api.themoviedb.org/3/movie/";
    private final static String API = "api_key";
    private final static String KEY_API = BuildConfig.MY_MOVIE_DB_API_KEY;
    private final static String IMAGE_URL_COMPELTE = "https://image.tmdb.org/t/p/original/";


    private static final int READTIMEOUT = 10000;
    private static final int CONNECTTIMEOUT = 15000;
    private static final String REQUEST_METHOD = "GET";


    private final static String RESULT = "results";
    private final static String TITLE = "original_title";
    private final static String OVER_VIEW = "overview";
    private final static String POSTER = "poster_path";
    private final static String VOTE = "vote_average";
    private final static String DATE = "release_date";

    public static URL createURL(String mUrl) {
        Uri builtUri = Uri.parse(BASE_URL_MOVIE).buildUpon()
                .appendPath(mUrl)
                .appendQueryParameter(API,KEY_API)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String ReadFromStream(InputStream inputStream) throws IOException {
        String Line = "";
        StringBuilder builder = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            while ((Line = reader.readLine()) != null) {
                builder.append(Line);
            }
        }
        return builder.toString();
    }


    public static String getResponseFromHttpUrl(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(READTIMEOUT);
            httpURLConnection.setConnectTimeout(CONNECTTIMEOUT);
            httpURLConnection.setRequestMethod(REQUEST_METHOD);
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = ReadFromStream(inputStream);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
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
                String title = currentMovie.getString(TITLE);
                String overview = currentMovie.getString(OVER_VIEW);
                String poster = currentMovie.getString(POSTER);
                String rate = currentMovie.getString(VOTE);
                String date = currentMovie.getString(DATE);
                String Image_Poster_Concat = IMAGE_URL_COMPELTE.concat(poster);
                movieList.add(new Movie(title, Image_Poster_Concat, overview, rate, date));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieList;
    }

    public static List<Movie> ExtractMovieData(String URL) {
        String jsonResponse = null;
        URL url = createURL(URL);
        try {
            jsonResponse = getResponseFromHttpUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Movie> movieDetails = extractFeatureFromJson(jsonResponse);
        return movieDetails;
    }
}
