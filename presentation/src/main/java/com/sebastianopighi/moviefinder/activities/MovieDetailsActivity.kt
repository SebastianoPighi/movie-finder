package com.sebastianopighi.moviefinder.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.sebastianopighi.moviefinder.R
import com.sebastianopighi.moviefinder.models.MovieDetails
import com.sebastianopighi.moviefinder.utils.BaseActivity
import com.sebastianopighi.moviefinder.utils.PicassoImageLoader
import com.sebastianopighi.moviefinder.viewmodels.MovieDetailsViewModel
import com.sebastianopighi.moviefinder.viewmodels.factories.MovieDetailsViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_details.*


class MovieDetailsActivity : BaseActivity() {

    private lateinit var movieDetailsViewModel: MovieDetailsViewModel
    private lateinit var movieBackdropImage: ImageView
    private lateinit var movieTitleLabel: TextView
    private lateinit var moviePlotOverviewLabel: TextView
    private var imageLoader: PicassoImageLoader? = null
    private var movieId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        setTitle(R.string.movie_details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        progressBar = movie_details_progress
        movieBackdropImage = movie_details_backdrop_image
        movieTitleLabel = movie_details_title
        moviePlotOverviewLabel = movie_details_plot_overview
        imageLoader = PicassoImageLoader(Picasso.with(this))
        movieId = intent.getIntExtra(MOVIE_ID, 0)

        val networkErrorObserver = Observer<String>() {
            progressBar.visibility = View.GONE
            it?.let { showDialog(getString(R.string.generic_networking_error_title), getString(R.string.generic_networking_error),  getString(R.string.retry)) }
        }

        val movieDetailsObserver = Observer<MovieDetails>() {
            progressBar.visibility = View.GONE
            it?.backdropPath?.let { imageLoader?.load(it, movieBackdropImage, true) }
            movieTitleLabel.text = it?.title
            moviePlotOverviewLabel.text = it?.overview
        }

        movieDetailsViewModel = ViewModelProviders.of(this, MovieDetailsViewModelFactory(getString(R.string.api_base_url),
                getString(R.string.api_key), movieId)).get(MovieDetailsViewModel::class.java)
        movieDetailsViewModel.getNetworkError().observe(this, networkErrorObserver)
        movieDetailsViewModel.getMovieDetails().observe(this, movieDetailsObserver)
        if(checkInternetConnection()) {
            movieDetailsViewModel.loadMovieDetails(movieId)
        }
    }

    override fun reloadData() {
        if (checkInternetConnection()) {
            progressBar.visibility = View.VISIBLE
            movieDetailsViewModel.loadMovieDetails(movieId)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

}
