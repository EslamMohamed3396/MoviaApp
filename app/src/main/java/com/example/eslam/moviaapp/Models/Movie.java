package com.example.eslam.moviaapp.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;


@Entity(tableName = "movie_table")
public class Movie {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int mIdDataBase;

    @ColumnInfo(name = "id_Movie")
    public String mId;

    @ColumnInfo(name = "title")
    public String mOriginal_Title;

    @ColumnInfo(name = "poster")
    public String mMovie_poster;

    @ColumnInfo(name = "overview")
    public String mOverview;

    @ColumnInfo(name = "rating")
    public String mRating;

    @ColumnInfo(name = "release_Date")
    public String mRelease_date;

    @ColumnInfo(name = "backGround")
    public String mBackGround;


    @Ignore
    public Movie(String mId, String mOriginal_Title, String mMovie_poster, String mOverview, String mRating, String mRelease_date, String mBackGround) {
        this.mId = mId;
        this.mOriginal_Title = mOriginal_Title;
        this.mMovie_poster = mMovie_poster;
        this.mOverview = mOverview;
        this.mRating = mRating;
        this.mRelease_date = mRelease_date;
        this.mBackGround = mBackGround;
    }

    public Movie(int mIdDataBase, String mId, String mOriginal_Title, String mMovie_poster, String mOverview, String mRating, String mRelease_date, String mBackGround) {
        this.mIdDataBase = mIdDataBase;
        this.mId = mId;
        this.mOriginal_Title = mOriginal_Title;
        this.mMovie_poster = mMovie_poster;
        this.mOverview = mOverview;
        this.mRating = mRating;
        this.mRelease_date = mRelease_date;
        this.mBackGround = mBackGround;
    }


    public int getmIdDataBase() {
        return mIdDataBase;
    }

    public String getmId() {
        return mId;
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

    public String getmBackGround() {
        return mBackGround;
    }
}
