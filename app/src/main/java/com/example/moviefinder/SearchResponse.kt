package com.example.moviefinder

/**
 * Represents the response from the API when searching for movies.
 * It contains the list of movies as per the search results, the total number of results found,
 * and a response flag to indicate if the search was successful ('True' or 'False').
 * This class is pivotal in handling the response from the movie database API.
 *
 * @see List for the collection of movie responses.
 *
 * @author Matt Gacek
 */

data class SearchResponse(
    val Search: List<MovieResponse>,
    val totalResults: String,
    val Response: String
)
