package com.example.eslam.moviaapp.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.eslam.moviaapp.DataBase.DataBaseMovie;
import com.example.eslam.moviaapp.Models.Review;

import java.util.List;

public class ReviewFavViewModel extends AndroidViewModel {
    private LiveData<List<Review>> review;

    public ReviewFavViewModel(@NonNull Application application) {
        super(application);
        DataBaseMovie dataBaseMovie = DataBaseMovie.getINSTANCE(this.getApplication());
        review = dataBaseMovie.movieDao().loadAllReview();
    }

    public LiveData<List<Review>> getReview() {
        return review;
    }
}
