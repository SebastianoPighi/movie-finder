package com.sebastianopighi.data.repositories

import com.sebastianopighi.data.models.mappers.MovieDataToMovieEntityMapper
import com.sebastianopighi.data.repositories.datastores.CacheDataStore
import com.sebastianopighi.data.repositories.datastores.CloudDataStore
import com.sebastianopighi.domain.models.ApiError
import com.sebastianopighi.domain.models.MovieDetailsEntity
import com.sebastianopighi.domain.models.MovieEntity
import com.sebastianopighi.domain.repositories.MoviesRepository

class MoviesRepositoryImpl(private val cacheDataStore: CacheDataStore,
                           private val cloudDataStore: CloudDataStore,
                           private val movieDataToMovieEntityMapper: MovieDataToMovieEntityMapper): MoviesRepository {

    override fun getRepoMovies(moviesPage: Int, callback: (List<MovieEntity>?, ApiError?) -> Unit) {
        if(moviesPage != 1) {
            cloudDataStore.getNextMovies(moviesPage) { movies, error ->
                error?.let {
                    callback(movies?.map { movieDataToMovieEntityMapper.mapFrom(it) }, error)
                    return@getNextMovies
                }
                movies?.let { cacheDataStore.saveAllMovies(it) }
                cacheDataStore.getMovies { cachedMovies, cacheError ->
                    callback(cachedMovies?.map { movieDataToMovieEntityMapper.mapFrom(it) }, cacheError)
                }
            }
        }
        cacheDataStore.isEmpty().let { empty ->
            if (!empty) {
                cacheDataStore.getMovies { movies, error ->
                    callback(movies?.map { movieDataToMovieEntityMapper.mapFrom(it) }, error)
                }
            } else {
                cloudDataStore.getMovies { movies, error ->
                    error?.let {
                        callback(movies?.map { movieDataToMovieEntityMapper.mapFrom(it) }, error)
                        return@getMovies
                    }
                    movies?.let { cacheDataStore.saveAllMovies(it) }
                    callback(movies?.map { movieDataToMovieEntityMapper.mapFrom(it) }, error)
                }
            }
        }
    }

    override fun getRepoMovieDetails(moviedId: Int, callback: (MovieDetailsEntity?, ApiError?) -> Unit) {
        cacheDataStore.isEmpty().let { empty ->
            if (!empty && cacheDataStore.containsMovieDetails(moviedId)) {
                cacheDataStore.getMovieDetails(moviedId) { movieDetails, error ->
                    callback(movieDataToMovieEntityMapper.mapFrom(movieDetails), error)
                }
            } else {
                cloudDataStore.getMovieDetails(moviedId) { movieDetails, error ->
                    error?.let {
                        callback(movieDataToMovieEntityMapper.mapFrom(movieDetails), error)
                        return@getMovieDetails
                    }
                    movieDetails?.let { cacheDataStore.saveMovieDetails(movieDetails) }
                    callback(movieDataToMovieEntityMapper.mapFrom(movieDetails), error)
                }
            }
        }
    }

    override fun searchRepoMovie(movieTitleQuery: String, callback: (List<MovieEntity>?, ApiError?) -> Unit) {
        cloudDataStore.searchMovie(movieTitleQuery) { movies, error ->
                            callback(movies?.map { movieDataToMovieEntityMapper.mapFrom(it) }, error)
        }
    }
}