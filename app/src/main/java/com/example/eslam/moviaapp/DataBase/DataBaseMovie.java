package com.example.eslam.moviaapp.DataBase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.eslam.moviaapp.Dao.MovieDao;
import com.example.eslam.moviaapp.Models.Movie;
import com.example.eslam.moviaapp.Models.Review;
import com.example.eslam.moviaapp.Models.Trailer;

@Database(entities = {
        Movie.class,
        Review.class,
        Trailer.class},
        version = 1,
        exportSchema = false)
public abstract class DataBaseMovie extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "movie";
    private static DataBaseMovie sINSTANCE;

    public static DataBaseMovie getINSTANCE(Context context) {
        if (sINSTANCE == null) {
            synchronized (LOCK) {
                sINSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        DataBaseMovie.class,
                        DataBaseMovie.DATABASE_NAME)
                        .build();
            }
        }
        return sINSTANCE;
    }

    public abstract MovieDao movieDao();
}
