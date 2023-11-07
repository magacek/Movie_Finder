package com.example.moviefinder

/**
 * Represents a single movie's response structure from the API.
 * It encapsulates the necessary information about a movie that will be used
 * by the application, such as the title, year of release, poster image URL, IMDb ID, and type (movie, series, etc.).
 *
 * @see Data class in Kotlin for the simplicity of creating a class for holding data.
 *
 * @author Matt Gacek
 */

data class MovieResponse(
    val Title: String,
    val Year: String,
    val Poster: String,
    val imdbID: String,
    val Type: String
)
