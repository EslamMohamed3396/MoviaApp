package com.example.eslam.moviaapp.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "trailer_table")
public class Trailer {

    @ColumnInfo(name = "id_trailer")
    public String mId_Trailer;

    @PrimaryKey(autoGenerate = true)
    public int mId;

    @ColumnInfo(name = "trailer")
    public String mTrailer;

    @Ignore
    public Trailer(String mId_Trailer, String mTrailer) {
        this.mId_Trailer = mId_Trailer;
        this.mTrailer = mTrailer;
    }

    public Trailer(String mId_Trailer, int mId, String mTrailer) {
        this.mId_Trailer = mId_Trailer;
        this.mId = mId;
        this.mTrailer = mTrailer;
    }

    public String getmId_Trailer() {
        return mId_Trailer;
    }

    public int getmId() {
        return mId;
    }

    public String getmTrailer() {
        return mTrailer;
    }
}

