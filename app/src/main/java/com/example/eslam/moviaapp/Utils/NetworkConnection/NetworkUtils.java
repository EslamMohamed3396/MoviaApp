package com.example.eslam.moviaapp.Utils.NetworkConnection;


import android.net.Uri;


import com.example.eslam.moviaapp.BuildConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public final class NetworkUtils {

    private final static String BASE_URL_MOVIE = "https://api.themoviedb.org/3/movie/";
    private final static String API = "api_key";
    private final static String KEY_API = BuildConfig.MY_MOVIE_DB_API_KEY;

    private static final int READTIMEOUT = 10000;
    private static final int CONNECTTIMEOUT = 15000;
    private static final String REQUEST_METHOD = "GET";


    public static URL createURL(String mUrl) {
        Uri builtUri = Uri.parse(BASE_URL_MOVIE).buildUpon()
                .appendPath(mUrl)
                .appendQueryParameter(API, KEY_API)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL createURL_Trailer(String murl) {
        Uri builtUri = Uri.parse(murl).buildUpon()
                .appendQueryParameter(API, KEY_API)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    private static String ReadFromStream(InputStream inputStream) throws IOException {
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



}
