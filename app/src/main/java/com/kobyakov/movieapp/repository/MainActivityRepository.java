package com.kobyakov.movieapp.repository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.kobyakov.movieapp.ClientRetrofit;
import com.kobyakov.movieapp.model.Movie;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivityRepository {

    private MutableLiveData<List<Movie>> movieListWithApi;
    private MutableLiveData<Integer> statusCode;

    private final String TAG = getClass().getSimpleName();
    private static final String API_KEY = "bc67f443127da8869d4ff54aa7094851";

    private String language = "en-US";
    private int page = 1;

    public MainActivityRepository() {
        statusCode = new MutableLiveData<>();
        movieListWithApi = new MutableLiveData<>();
    }

    public MutableLiveData<List<Movie>> getMovieListWithApi() {
        return movieListWithApi;
    }

    public MutableLiveData<Integer> getStatusCode() {
        return statusCode;
    }

    public void getMovieList() {
        Log.d(TAG, "NETWORK_REQUEST");
        Disposable disposable = initRetrofitRx().getMovieList(API_KEY, language, page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                        response -> {
                            if (response.body() != null && response.body().getMovies() != null) {
                                movieListWithApi.postValue(response.body().getMovies());
                            }

                            if (response.code() != 200) {
                                statusCode.postValue(response.code());
                            }

                        },
                        error -> statusCode.postValue(-200)
                );
    }

    private static ClientRetrofit initRetrofitRx() {
        return new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(ClientRetrofit.class);
    }
}