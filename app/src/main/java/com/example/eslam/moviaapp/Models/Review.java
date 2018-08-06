package com.example.eslam.moviaapp.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;


@Entity(tableName = "review_table")
public class Review {

    @PrimaryKey(autoGenerate = true)
    public int mId;

    @ColumnInfo(name = "id_review")
    public String mId_Review;



    @ColumnInfo(name = "author")
    public String mAuthor;

    @ColumnInfo(name = "content")
    public String mContent = HAS_CONTENT;


    public static String HAS_CONTENT = "No Content";

    @Ignore
    public Review(String mId_Review, String mAuthor, String mContent) {
        this.mId_Review = mId_Review;
        this.mAuthor = mAuthor;
        this.mContent = mContent;
    }

    @Ignore
    public Review() {
    }

    public Review(int mId, String mId_Review, String mAuthor, String mContent) {
        this.mId = mId;
        this.mId_Review = mId_Review;
        this.mAuthor = mAuthor;
        this.mContent = mContent;
    }



    public int getmId() {
        return mId;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public String getmContent() {
        return mContent;
    }

    public String getmId_Review() {
        return mId_Review;
    }

    public boolean hasContent() {
        return mContent != HAS_CONTENT;
    }

}
