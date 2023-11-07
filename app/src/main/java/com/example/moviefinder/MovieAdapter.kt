package com.example.moviefinder


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
/**
 * Provides a reference to the views for each data item. Complex data items may need more than one view per item,
 * and the view holder objects provide access to all the views for a data item in a recycle view.
 * This class is used to hold the widgets in the layout that will display a movie item (like title, year, type, etc.).
 *
 * @see RecyclerView.ViewHolder for the description of view holders in the context of RecyclerView.
 * @see View for the UI component that this holder will be holding.
 *
 * @author Matt Gacek
 */



class MoviesAdapter(private val moviesList: List<MovieResponse>) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewTitle: TextView = view.findViewById(R.id.item_movie_title)
        val textViewYear: TextView = view.findViewById(R.id.item_movie_year)
        val textViewType: TextView = view.findViewById(R.id.item_movie_type)
        val textViewImdb: TextView = view.findViewById(R.id.item_movie_imdb)
        val imageViewPoster: ImageView = view.findViewById(R.id.item_movie_poster)
        val buttonShare: Button = view.findViewById(R.id.item_movie_share)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = moviesList[position]
        holder.textViewTitle.text = movie.Title
        holder.textViewYear.text = movie.Year
        holder.textViewType.text = movie.Type
        holder.textViewImdb.text = "https://www.imdb.com/title/${movie.imdbID}/"

        Glide.with(holder.itemView.context)
            .load(movie.Poster)
            .into(holder.imageViewPoster)

        holder.buttonShare.setOnClickListener {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Check out this movie: http://www.imdb.com/title/${movie.imdbID}")
                type = "text/plain"
            }
            holder.itemView.context.startActivity(Intent.createChooser(shareIntent, "Share via"))
        }
    }

    override fun getItemCount() = moviesList.size
}
