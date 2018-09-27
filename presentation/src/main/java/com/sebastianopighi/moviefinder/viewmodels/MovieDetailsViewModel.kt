package com.sebastianopighi.moviefinder.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.sebastianopighi.data.models.mappers.MovieDataToMovieEntityMapper
import com.sebastianopighi.data.networking.MoviesApiImpl
import com.sebastianopighi.data.repositories.MoviesCacheImpl
import com.sebastianopighi.data.repositories.MoviesRepositoryImpl
import com.sebastianopighi.data.repositories.datastores.CacheDataStore
import com.sebastianopighi.data.repositories.datastores.CloudDataStore
import com.sebastianopighi.domain.usecases.MovieDetailsUseCase
import com.sebastianopighi.moviefinder.models.MovieDetails
import com.sebastianopighi.moviefinder.models.mappers.MovieEntityToMovieMapper



class MovieDetailsViewModel (private val apiBaseUrl: String, private val apiKey: String,
                             private val movieId: Int): ViewModel() {

    private var movieDetailsUseCase: MovieDetailsUseCase? = null
    private var movieEntityToMovieMapper: MovieEntityToMovieMapper
    private var networkError: MutableLiveData<String>? = null
    private var movieDetails: MutableLiveData<MovieDetails>? = null

    init {
        val cacheDataStore = CacheDataStore(MoviesCacheImpl)
        val moviesApi = MoviesApiImpl(apiBaseUrl, apiKey)
        val cloudDataStore = CloudDataStore(moviesApi)
        val movieDataToMovieEntityMapper = MovieDataToMovieEntityMapper()
        val moviesRepository = MoviesRepositoryImpl(cacheDataStore, cloudDataStore, movieDataToMovieEntityMapper)
        movieDetailsUseCase = MovieDetailsUseCase(moviesRepository)
        movieEntityToMovieMapper = MovieEntityToMovieMapper()
    }

    fun getNetworkError(): MutableLiveData<String> {
        if (networkError == null) {
            networkError = MutableLiveData()
        }
        return networkError as MutableLiveData<String>
    }

    fun getMovieDetails(): MutableLiveData<MovieDetails> {
        if (movieDetails == null) {
            movieDetails = MutableLiveData()
        }
        return movieDetails as MutableLiveData<MovieDetails>
    }

    fun loadMovieDetails(movieId: Int) {
        movieDetailsUseCase?.getMovieDetails(movieId) { movieDetails, error ->
            error?.let {
                networkError?.value = error.errorMessage
                return@getMovieDetails
            }
            this.movieDetails?.value = movieEntityToMovieMapper.mapFrom(movieDetails)
        }
    }
}