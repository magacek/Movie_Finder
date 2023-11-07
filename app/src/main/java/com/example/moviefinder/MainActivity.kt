package com.example.moviefinder

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Acts as the central activity for the application, providing a layout with a search functionality
 * to look up movies by title. It initializes and sets up the RecyclerView that displays movie items
 * and handles action events like searching for movies and sending feedback via email.
 * This class serves as a controller that manages communication between the view (UI) and the model (data).
 *
 * @see AppCompatActivity for the basic application support for the window decor and user interaction.
 * @see RecyclerView for displaying the list of movies in a scrollable list.
 * @see MoviesAdapter for adapting the movie data into the RecyclerView.
 * @see sendFeedbackEmail for sending feedback via the user's email client.
 * @see searchMovies for performing the movie search operation and updating the UI with results.
 *
 * @author Matt Gacek
 */

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var moviesAdapter: MoviesAdapter
    private var moviesList: MutableList<MovieResponse> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        recyclerView = findViewById(R.id.recyclerViewMovies)
        recyclerView.layoutManager = LinearLayoutManager(this)
        moviesAdapter = MoviesAdapter(moviesList)
        recyclerView.adapter = moviesAdapter

        val buttonSearch = findViewById<Button>(R.id.buttonSearch)
        buttonSearch.setOnClickListener {
            val movieTitle = findViewById<EditText>(R.id.editTextMovieTitle).text.toString().trim()
            if (movieTitle.isNotEmpty()) {
                searchMovies(movieTitle)
            } else {
                Toast.makeText(this, "Please enter a movie title.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_send_feedback -> {
                sendFeedbackEmail()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun sendFeedbackEmail() {
        val emailIntent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"
            putExtra(Intent.EXTRA_EMAIL, arrayOf("dev@developer.app"))
            putExtra(Intent.EXTRA_SUBJECT, "Feedback")
        }
        if (emailIntent.resolveActivity(packageManager) != null) {
            startActivity(Intent.createChooser(emailIntent, "Choose an email client:"))
        } else {
            Toast.makeText(this, "No email app available", Toast.LENGTH_SHORT).show()
        }
    }


    private fun searchMovies(query: String) {
        val call = RetrofitClient.instance.create(ApiService::class.java)
            .searchMovies(apiKey = "9e17ba6f", searchQuery = query)

        call.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                if (response.isSuccessful && response.body()?.Response == "True") {
                    response.body()?.Search?.let { searchResults ->
                        moviesList.clear()
                        moviesList.addAll(searchResults)
                        moviesAdapter.notifyDataSetChanged()
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Movie not found!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "An error occurred: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
