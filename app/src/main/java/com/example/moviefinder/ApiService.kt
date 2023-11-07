package com.example.moviefinder

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Defines the communication with the movie database API using HTTP GET requests.
 * This interface is used by Retrofit to turn HTTP API into a Kotlin interface.
 * The searchMovies function is designed to query movies based on a search string
 * and an API key.
 *
 * @param apiKey The API key required to access the movie database service.
 * @param searchQuery The user's query string to search for movies.
 * @return A call object which will emit a SearchResponse upon execution.
 *
 * @author Matt Gacek
 */

interface ApiService {
    @GET(".")
    fun searchMovies(
        @Query("apikey") apiKey: String,
        @Query("s") searchQuery: String
    ): Call<SearchResponse>
}

