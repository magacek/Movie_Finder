package com.example.moviefinder
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonSearch = findViewById<Button>(R.id.buttonSearch)
        buttonSearch.setOnClickListener {
            val movieTitle = findViewById<EditText>(R.id.editTextMovieTitle).text.toString()
            if (movieTitle.isNotEmpty()) {
                getMovieDetails(movieTitle)
            } else {
                Toast.makeText(this, "Please enter a movie title.", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun getMovieDetails(title: String) {
        val call = RetrofitClient.instance.create(ApiService::class.java).getMovieDetails(apiKey = "9e17ba6f", title = title)
        call.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    val movie = response.body()
                    val imageViewPoster = findViewById<ImageView>(R.id.imageViewPoster)
                    Glide.with(this@MainActivity)
                        .load(movie?.Poster)
                        .into(imageViewPoster)

                    // Update the TextViews with movie information
                    findViewById<TextView>(R.id.textViewTitle).text = movie?.Title
                    findViewById<TextView>(R.id.textViewYear).text = "Year: ${movie?.Year}"
                    findViewById<TextView>(R.id.textViewRating).text = "Rated: ${movie?.Rated}"
                    findViewById<TextView>(R.id.textViewRuntime).text = "Runtime: ${movie?.Runtime}"
                    findViewById<TextView>(R.id.textViewGenre).text = "Genre: ${movie?.Genre}"
                    findViewById<TextView>(R.id.textViewIMDbRating).text = "IMDb Rating: ${movie?.imdbRating}"

                    // IMDb link TextView click listener
                    val imdbLinkTextView = findViewById<TextView>(R.id.textViewIMDbLink)
                    imdbLinkTextView.text = "IMDb: ${movie?.imdbID}"
                    imdbLinkTextView.setOnClickListener {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.imdb.com/title/${movie?.imdbID}"))
                        startActivity(intent)
                    }

                    // Share button click listener
                    val shareButton = findViewById<Button>(R.id.buttonShare)
                    shareButton.setOnClickListener {
                        val shareIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, "Check out this movie: ${movie?.Title} on IMDb: https://www.imdb.com/title/${movie?.imdbID}")
                            type = "text/plain"
                        }
                        startActivity(Intent.createChooser(shareIntent, "Share via"))
                    }
                } else {
                    Log.e("MainActivity", "Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.e("MainActivity", "Failure: ${t.message}")
            }
        })
    }
}