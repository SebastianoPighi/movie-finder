package com.sebastianopighi.moviefinder.viewmodels.factories

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.sebastianopighi.moviefinder.viewmodels.MoviesViewModel

class MoviesViewModelFactory(private val apiBaseUrl: String, private val apiKey: String): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MoviesViewModel(apiBaseUrl, apiKey) as T
    }

}