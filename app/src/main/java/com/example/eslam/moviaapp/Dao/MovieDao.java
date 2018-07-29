package com.example.eslam.moviaapp.Dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.eslam.moviaapp.Models.Movie;

import java.util.List;

@android.arch.persistence.room.Dao
public interface MovieDao {

    @Insert
    void insertMovie(Movie movie);

    @Query("SELECT * FROM movie_table ORDER BY rating DESC")
    LiveData<List<Movie>> loadAllMovie();

    @Query("DELETE FROM movie_table WHERE id_Movie = :favMovieId")
    int deleteMovieById(String favMovieId);
}
