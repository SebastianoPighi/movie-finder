package com.sebastianopighi.moviefinder.viewmodels.factories

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.sebastianopighi.moviefinder.viewmodels.MovieDetailsViewModel

class MovieDetailsViewModelFactory(private val apiBaseUrl: String, private val apiKey: String,
                                   private val movieId: Int): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieDetailsViewModel(apiBaseUrl, apiKey, movieId) as T
    }

}