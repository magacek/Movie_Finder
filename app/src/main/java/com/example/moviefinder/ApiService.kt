package com.example.moviefinder

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(".")
    fun getMovieDetails(
        @Query("apikey") apiKey: String,
        @Query("t") title: String
    ): Call<MovieResponse>
}
