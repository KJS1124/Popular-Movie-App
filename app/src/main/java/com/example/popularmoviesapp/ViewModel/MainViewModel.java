package com.example.popularmoviesapp.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.popularmoviesapp.model.Movie;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<Movie>> movies;

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Movie>> getMovies(){
        return this.movies;
    }
}
