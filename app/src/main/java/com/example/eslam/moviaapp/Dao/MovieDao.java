package com.example.eslam.moviaapp.Dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.eslam.moviaapp.Models.Movie;
import com.example.eslam.moviaapp.Models.Review;
import com.example.eslam.moviaapp.Models.Trailer;

import java.util.List;

@android.arch.persistence.room.Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(Movie movie);

    @Query("SELECT * FROM movie_table ORDER BY rating DESC")
    LiveData<List<Movie>> loadAllMovie();

    @Query("SELECT * FROM movie_table WHERE id_Movie=:mId")
    LiveData<List<Movie>> loadAllMovieByID(String mId);

    @Query("DELETE FROM movie_table WHERE id_Movie = :favMovieId")
    int deleteMovieById(String favMovieId);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertReview(List<Review> review);

    @Query("SELECT * FROM review_table")
    LiveData<List<Review>> loadAllReview();

    @Query("DELETE FROM review_table WHERE id_review = :favReviewId")
    int deleteReviewById(String favReviewId);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTrailer(List<Trailer> trailers);

    @Query("SELECT * FROM trailer_table")
    LiveData<List<Trailer>> loadAllTrailer();

    @Query("DELETE FROM trailer_table WHERE id_trailer = :favTrailerId")
    int deleteTrailerById(String favTrailerId);


}
