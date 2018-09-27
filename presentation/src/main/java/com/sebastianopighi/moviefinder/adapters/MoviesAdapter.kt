package com.sebastianopighi.moviefinder.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sebastianopighi.moviefinder.R
import com.sebastianopighi.moviefinder.models.Movie
import com.sebastianopighi.moviefinder.utils.PicassoImageLoader
import kotlinx.android.synthetic.main.movies_adapter_cell.view.*

class MoviesAdapter(private val imageLoader: PicassoImageLoader,
                    private val onMovieSelected: (Movie, View) -> Unit) : RecyclerView.Adapter<MoviesAdapter.MovieCellViewHolder>() {

    private val movies: MutableList<Movie> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCellViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(
                R.layout.movies_adapter_cell,
                parent,
                false)
        return MovieCellViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MovieCellViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie, imageLoader, onMovieSelected)
    }

    fun addMovies(movies: List<Movie>) {
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    fun reloadMovies(movies: List<Movie>) {
        this.movies.clear()
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    class MovieCellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie, imageLoader: PicassoImageLoader, listener: (Movie, View) -> Unit) = with(itemView) {
            movie.posterPath?.let { imageLoader.load(it, movie_image, true) }
            setOnClickListener { listener(movie, itemView) }
        }

    }
}