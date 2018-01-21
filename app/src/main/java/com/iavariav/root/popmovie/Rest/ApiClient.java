package com.iavariav.root.popmovie.Rest;

import com.iavariav.root.popmovie.Model.MovieModel;
import com.iavariav.root.popmovie.Model.MovieModelBaru;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by root on 1/20/18.
 */

public interface ApiClient {
    @GET("movie/popular?api_key=0dde3e9896a8c299d142e214fcb636f8&language=en-US&page=1")
    Call<MovieModelBaru> ambilFilmPopuler();

    //ini link top rated
    @GET("movie/popular?api_key=0dde3e9896a8c299d142e214fcb636f8&language=en-US&page=1")
    Call<MovieModelBaru> ambilTopRatedFil();
}
