package com.example.eslam.moviaapp.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.eslam.moviaapp.DataBase.DataBaseMovie;
import com.example.eslam.moviaapp.Models.Movie;

import java.util.List;

public class MainFavViewModel extends AndroidViewModel {
    private LiveData<List<Movie>> movie;

    public MainFavViewModel(@NonNull Application application) {
        super(application);
        DataBaseMovie dataBaseMovie = DataBaseMovie.getINSTANCE(this.getApplication());
        movie = dataBaseMovie.movieDao().loadAllMovie();
    }

    public LiveData<List<Movie>> getMovie() {
        return movie;
    }

}
