package com.example.popularmoviesapp.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.popularmoviesapp.model.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("Select * from movie")
    LiveData<List<Movie>> loadAllMovies();

    @Insert
    void insertMovie(Movie movie);

    @Update
    void updateMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);
}
