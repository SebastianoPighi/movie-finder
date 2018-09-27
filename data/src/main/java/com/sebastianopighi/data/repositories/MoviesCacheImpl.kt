package com.sebastianopighi.data.repositories

import com.sebastianopighi.data.models.MovieData
import com.sebastianopighi.data.models.MovieDetailsData

object MoviesCacheImpl: MoviesCache {

    private val movies = mutableMapOf<Int, MovieData>()
    private val moviesDetails = mutableMapOf<Int, MovieDetailsData>()

    override fun saveMovie(movieData: MovieData) {
        movies.put(movieData.id, movieData)
    }

    override fun removeMovie(movieData: MovieData) {
        movies.remove(movieData.id)
    }

    override fun saveAllMovies(movieData: List<MovieData>) {
        movieData.forEach { movies.put(it.id, it) }
    }

    override fun getAllMovies(): List<MovieData> {
        return movies.values.toList()
    }

    override fun getMovie(movieId: Int): MovieData {
        return movies.get(movieId)!!
    }

    override fun searchMovie(movieTitleQuery: String): List<MovieData> {
        return movies.values.filter { it.title.contains(movieTitleQuery) || it.originalTitle.contains(movieTitleQuery) }
    }

    override fun containsMovie(movieId: Int): Boolean {
        return movies.containsKey(movieId)
    }

    override fun saveMovieDetails(movieDetailsData: MovieDetailsData) {
        moviesDetails.put(movieDetailsData.id, movieDetailsData)
    }

    override fun removeMovieDetails(movieDetailsData: MovieDetailsData) {
        moviesDetails.remove(movieDetailsData.id)
    }

    override fun saveAllMoviesDetails(movieDetailsData: List<MovieDetailsData>) {
        movieDetailsData.forEach { moviesDetails.put(it.id, it) }
    }

    override fun getAllMoviesDetails(): List<MovieDetailsData> {
        return moviesDetails.values.toList()
    }

    override fun getMovieDetails(movieId: Int): MovieDetailsData {
        return moviesDetails.get(movieId)!!
    }

    override fun searchMovieDetails(movieTitleQuery: String): List<MovieDetailsData> {
        return moviesDetails.values.filter { it.title.contains(movieTitleQuery) || it.originalTitle.contains(movieTitleQuery) }
    }

    override fun containsMovieDetails(movieId: Int): Boolean {
        return moviesDetails.containsKey(movieId)
    }

    override fun clear() {
        movies.clear()
        moviesDetails.clear()
    }

    override fun isEmpty(): Boolean {
        return movies.isEmpty() && moviesDetails.isEmpty()
    }
}