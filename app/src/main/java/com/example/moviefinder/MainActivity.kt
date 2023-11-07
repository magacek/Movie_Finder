package com.example.moviefinder
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
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
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.main, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.action_feedback -> {
//                // Write code to handle feedback action
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
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