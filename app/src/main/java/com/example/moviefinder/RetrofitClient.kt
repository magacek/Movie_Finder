package com.example.moviefinder

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Provides a singleton Retrofit instance for making HTTP requests.
 * This object encapsulates the base URL for the API and the JSON converter factory
 * to be used by Retrofit for serializing and deserializing objects.
 * It is used throughout the application to create instances of the ApiService interface.
 *
 * @see Retrofit.Builder for the construction of the Retrofit instance.
 * @see GsonConverterFactory for converting JSON strings to Kotlin objects.
 *
 * @author Matt Gacek
 */

object RetrofitClient {
    private const val BASE_URL = "https://www.omdbapi.com/"

    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
