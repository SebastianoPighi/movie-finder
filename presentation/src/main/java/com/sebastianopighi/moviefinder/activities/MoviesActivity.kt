package com.sebastianopighi.moviefinder.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import com.sebastianopighi.moviefinder.R
import com.sebastianopighi.moviefinder.adapters.EndlessRecyclerViewScrollListener
import com.sebastianopighi.moviefinder.adapters.MoviesAdapter
import com.sebastianopighi.moviefinder.models.Movie
import com.sebastianopighi.moviefinder.utils.BaseActivity
import com.sebastianopighi.moviefinder.utils.PicassoImageLoader
import com.sebastianopighi.moviefinder.viewmodels.MoviesViewModel
import com.sebastianopighi.moviefinder.viewmodels.factories.MoviesViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movies.*


class MoviesActivity : BaseActivity(), MenuItem.OnActionExpandListener, SearchView.OnQueryTextListener {

    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var moviesAdapter: MoviesAdapter
    private var searchMovieMenu: Menu? = null
    private var searchViewItem: MenuItem? = null
    private var searchView: SearchView? = null
    private var movieTitleQuery: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        setTitle(R.string.movies)

        progressBar = movies_progress
        recyclerView = movies_recyclerview

        if(getString(R.string.api_key) == "Paste here your API key") {
            showNoAPIKeyDialog()
            return
        }

        moviesAdapter = MoviesAdapter(PicassoImageLoader(Picasso.with(this))) { movie, _ ->
            openMovieDetails(movie.id)
        }
        val layoutManager = GridLayoutManager(applicationContext, 2)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = moviesAdapter
        recyclerView.addOnScrollListener(object : EndlessRecyclerViewScrollListener(layoutManager){
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                Toast.makeText(this@MoviesActivity, R.string.loading_new_movies, Toast.LENGTH_SHORT).show()
                moviesViewModel.loadNextMovies()
            }
        })

        val networkErrorObserver = Observer<String>() {
            progressBar.visibility = View.GONE
            it?.let { showDialog(getString(R.string.generic_networking_error_title), getString(R.string.generic_networking_error),  getString(R.string.retry)) }
        }

        val moviesObserver = Observer<List<Movie>>() {
            progressBar.visibility = View.GONE
            it?.let { moviesAdapter.reloadMovies(it) }
        }

        moviesViewModel = ViewModelProviders.of(this, MoviesViewModelFactory(getString(R.string.api_base_url),
                getString(R.string.api_key))).get(MoviesViewModel::class.java)
        moviesViewModel.getNetworkError().observe(this, networkErrorObserver)
        moviesViewModel.getMovies().observe(this, moviesObserver)

        if(savedInstanceState != null) {
            movieTitleQuery = savedInstanceState.getString(MOVIE_TITLE_QUERY)
        }

        if (checkInternetConnection()) {
            moviesViewModel.loadMovies()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putString(MOVIE_TITLE_QUERY, movieTitleQuery)
        super.onSaveInstanceState(outState)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        if(!movieTitleQuery.isNullOrEmpty()) {
            Handler().post {
                searchView.let {
                    it?.clearFocus()
                    it?.isIconified = false
                    searchViewItem.let {
                        it?.expandActionView()
                    }
                    it?.setQuery(movieTitleQuery, false)
                }
            }

        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        configureSearchView(menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        closeSearchView()
        return false
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (!query.isNullOrEmpty()) {
            movieTitleQuery = query
            if (progressBar.visibility != View.VISIBLE) {
                progressBar.visibility = View.VISIBLE
            }
            moviesViewModel.searchMovieByTitle(query)
        }
        return false
    }

    override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
        movieTitleQuery = null
        moviesViewModel.loadMovies()
        return true
    }

    override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
        moviesAdapter.reloadMovies(listOf())
        return true
    }

    override fun reloadData() {
        if (checkInternetConnection()) {
            progressBar.visibility = View.VISIBLE
            movieTitleQuery?.let {
                moviesViewModel.searchMovieByTitle(movieTitleQuery)
                return
            }
            moviesViewModel.loadMovies()
        }
    }

    private fun configureSearchView(menu: Menu) {
        searchMovieMenu = menu
        searchViewItem = menu.findItem(R.id.movie_search)
        searchViewItem?.setOnActionExpandListener(this)
        searchView = searchViewItem?.actionView as SearchView
        searchView?.queryHint = getString(R.string.search_movie)
        searchView?.setIconifiedByDefault(false)
        searchView?.setOnQueryTextListener(this)
        val searchPlate = searchView?.findViewById<EditText>(R.id.search_src_text)
        searchPlate?.setOnEditorActionListener { textView, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (textView.text.isNullOrEmpty()) {
                    closeSearchView()
                }
                searchView?.clearFocus()
                true
            } else {
                false
            }
        }
    }

    private fun closeSearchView() {
        movieTitleQuery = null
        searchView?.isIconified = true
        searchView?.setQuery("", false)
        searchView?.clearFocus()
        searchViewItem?.collapseActionView()
    }

    private fun openMovieDetails(movieId: Int) {
        val detailsIntent = Intent(this, MovieDetailsActivity::class.java)
        detailsIntent.putExtra(MOVIE_ID, movieId)
        startActivity(detailsIntent)
    }
}
