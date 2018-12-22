package com.kobyakov.movieapp.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.kobyakov.movieapp.model.Movie;
import com.kobyakov.movieapp.repository.MainActivityRepository;

import java.util.List;

public class MainActivityViewModel extends ViewModel {
    LiveData<List<Movie>> movieList;
    LiveData<Integer> statusCode;
    MainActivityRepository repository;

    public MainActivityViewModel() {
        repository = new MainActivityRepository();
        repository.getMovieList();
        movieList = repository.getMovieListWithApi();
        statusCode = repository.getStatusCode();
    }

    public LiveData<List<Movie>> getMovieList() {
        return movieList;
    }

    public LiveData<Integer> getStatusCode() {
        return statusCode;
    }

    public void repeatRequest() {
        repository.getMovieList();
    }
}