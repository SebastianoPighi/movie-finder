package com.sebastianopighi.data.networking

import com.sebastianopighi.data.models.MovieData
import com.sebastianopighi.data.models.MovieDetailsData
import com.sebastianopighi.domain.models.ApiError
import com.sebastianopighi.domain.models.ApiErrorCode
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MoviesApiImpl(private val baseUrl: String,
                    private val apiKey: String = "") {

    private val moviesApiService: MoviesApi

    fun <T> apiCallback(success: ((Response<T>) -> Unit)?, failure: ((t: Throwable) -> Unit)? = null): Callback<T> {
        return object : Callback<T> {
            override fun onResponse(call: Call<T>, response: retrofit2.Response<T>) { success?.invoke(response) }
            override fun onFailure(call: Call<T>, t: Throwable) { failure?.invoke(t) }
        }
    }

    init {
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.readTimeout(20, TimeUnit.SECONDS)
        clientBuilder.connectTimeout(20, TimeUnit.SECONDS)
        if (!apiKey.isNullOrEmpty()) {
             val keyInterceptor = Interceptor { chain ->
                val original = chain.request()
                val originalHttpUrl = original.url()
                val url = originalHttpUrl.newBuilder()
                        .addQueryParameter("api_key", apiKey)
                        .build()
                val requestBuilder = original.newBuilder()
                        .url(url)
                val request = requestBuilder.build()
                return@Interceptor chain.proceed(request)
            }
            clientBuilder.addInterceptor(keyInterceptor)
        }
        moviesApiService = Retrofit.Builder()
                .client(clientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build()
                .create(MoviesApi::class.java)
    }


    fun getMovies(callback: (List<MovieData>?, ApiError?) -> Unit) {
        return moviesApiService.getMovies(1)
                .enqueue(apiCallback(
                        { r -> if(r.isSuccessful) callback(r.body()?.movies, null)
                            else callback(null, ApiError(ApiErrorCode.map(r.code()), r.message()))},
                        { t -> callback(null, ApiError(ApiErrorCode.GENERIC, t.message)) }))
    }

    fun getNextMovies(moviesPage:Int, callback: (List<MovieData>?, ApiError?) -> Unit) {
        return moviesApiService.getMovies(moviesPage)
                .enqueue(apiCallback(
                        { r -> if(r.isSuccessful) callback(r.body()?.movies, null)
                        else callback(null, ApiError(ApiErrorCode.map(r.code()), r.message()))},
                        { t -> callback(null, ApiError(ApiErrorCode.GENERIC, t.message)) }))
    }

    fun getMovieDetails(movieId: Int, callback: (MovieDetailsData?, ApiError?) -> Unit) {
        return moviesApiService.getMovieDetails(movieId)
                .enqueue(apiCallback(
                        { r -> if(r.isSuccessful) callback(r.body(), null)
                            else callback(null, ApiError(ApiErrorCode.map(r.code()), r.message()))},
                        { t -> callback(null, ApiError(ApiErrorCode.GENERIC, t.message)) }))
    }

    fun searchMovie(movieTitleQuery: String, callback: (List<MovieData>?, ApiError?) -> Unit) {
        return moviesApiService.searchMovies(movieTitleQuery)
                .enqueue(apiCallback(
                        { r -> if(r.isSuccessful) callback(r.body()?.movies, null)
                            else callback(null, ApiError(ApiErrorCode.map(r.code()), r.message()))},
                        { t -> callback(null, ApiError(ApiErrorCode.GENERIC, t.message)) }))
    }
}