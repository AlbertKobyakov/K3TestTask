package com.kobyakov.movieapp;

import com.kobyakov.movieapp.model.ResponseWithApi;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ClientRetrofit {
    @GET("/3/movie/popular")
    Single<Response<ResponseWithApi>> getMovieList(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page

    );
}