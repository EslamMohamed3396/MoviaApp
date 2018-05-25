package com.example.eslam.moviaapp;

public class Movie {
    private String mOriginal_Title;
    private String mMovie_poster;
    private String mOverview;
    private String mRating;
    private String mRelease_date;

    public Movie(String mOriginal_Title, String mMovie_poster, String mOverview, String mRating, String mRelease_date) {
        this.mOriginal_Title = mOriginal_Title;
        this.mMovie_poster = mMovie_poster;
        this.mOverview = mOverview;
        this.mRating = mRating;
        this.mRelease_date = mRelease_date;
    }

    public Movie() {

    }

    public String getmOriginal_Title() {
        return mOriginal_Title;
    }

    public String getmMovie_poster() {
        return mMovie_poster;
    }

    public String getmOverview() {
        return mOverview;
    }

    public String getmRating() {
        return mRating;
    }

    public String getmRelease_date() {
        return mRelease_date;
    }
}
