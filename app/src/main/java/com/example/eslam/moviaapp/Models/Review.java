package com.example.eslam.moviaapp.Models;

public class Review {

    private String mAuthor;
    private String mContent = HAS_CONTENT;
    private static String HAS_CONTENT = "No Content";

    public Review() {

    }

    public Review(String mAuthor, String mContent) {
        this.mAuthor = mAuthor;
        this.mContent = mContent;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public String getmContent() {
        return mContent;
    }

    public boolean hasContent() {
        return mContent != HAS_CONTENT;
    }

}
