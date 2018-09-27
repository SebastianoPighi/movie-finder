package com.sebastianopighi.moviefinder.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.sebastianopighi.data.models.mappers.MovieDataToMovieEntityMapper
import com.sebastianopighi.data.networking.MoviesApiImpl
import com.sebastianopighi.data.repositories.MoviesCacheImpl
import com.sebastianopighi.data.repositories.MoviesRepositoryImpl
import com.sebastianopighi.data.repositories.datastores.CacheDataStore
import com.sebastianopighi.data.repositories.datastores.CloudDataStore
import com.sebastianopighi.domain.usecases.MoviesUseCase
import com.sebastianopighi.moviefinder.models.Movie
import com.sebastianopighi.moviefinder.models.mappers.MovieEntityToMovieMapper

class MoviesViewModel (private val apiBaseUrl: String, private val apiKey: String): ViewModel() {

    private var getMoviesUseCase: MoviesUseCase? = null
    private var movieEntityToMovieMapper: MovieEntityToMovieMapper
    private var networkError: MutableLiveData<String>? = null
    private var movies: MutableLiveData<List<Movie>>? = null
    private var moviesApiPage: Int = 1

    init {
        val cacheDataStore = CacheDataStore(MoviesCacheImpl)
        val moviesApi = MoviesApiImpl(apiBaseUrl, apiKey)
        val cloudDataStore = CloudDataStore(moviesApi)
        val movieDataToMovieEntityMapper = MovieDataToMovieEntityMapper()
        val moviesRepository = MoviesRepositoryImpl(cacheDataStore, cloudDataStore, movieDataToMovieEntityMapper)
        getMoviesUseCase = MoviesUseCase(moviesRepository)
        movieEntityToMovieMapper = MovieEntityToMovieMapper()
    }

    fun getNetworkError(): MutableLiveData<String> {
        if (networkError == null) {
            networkError = MutableLiveData()
        }
        return networkError as MutableLiveData<String>
    }

    fun getMovies(): MutableLiveData<List<Movie>> {
        if (movies == null) {
            movies = MutableLiveData()
        }
        return movies as MutableLiveData<List<Movie>>
    }

    fun loadMovies() {
        getMoviesUseCase?.getMovies(moviesApiPage) { movies, error ->
            error?.let {
                networkError?.value = error.errorMessage
                return@getMovies
            }
            val mappedMovies = movies?.map { movieEntityToMovieMapper.mapFrom(it) }
            this.movies?.value = mappedMovies
        }
    }

    fun loadNextMovies() {
        getMoviesUseCase?.getMovies(moviesApiPage+1) { movies, error ->
            error?.let {
                networkError?.value = error.errorMessage
                return@getMovies
            }
            moviesApiPage+=1
            val mappedMovies = movies?.map { movieEntityToMovieMapper.mapFrom(it) }
            this.movies?.value = mappedMovies
        }
    }

    fun searchMovieByTitle(movieQueryTitle: String?) {
        movieQueryTitle?.let {
            movies?.value = null
            getMoviesUseCase?.searchMovie(movieQueryTitle) { movies, error ->
                error?.let {
                    networkError?.value = error.errorMessage
                    return@searchMovie
                }
                val mappedMovies = movies?.map { movieEntityToMovieMapper.mapFrom(it) }
                this.movies?.value = mappedMovies
            }
        }
    }


}