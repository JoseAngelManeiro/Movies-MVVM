package com.joseangelmaneiro.movies.platform.features.list

import android.os.Bundle
import androidx.lifecycle.Observer
import com.joseangelmaneiro.movies.R
import com.joseangelmaneiro.movies.platform.Status
import com.joseangelmaneiro.movies.platform.features.BaseActivity
import com.joseangelmaneiro.movies.platform.navigateToDetail
import kotlinx.android.synthetic.main.activity_movie_list.*
import org.koin.android.viewmodel.ext.android.viewModel

class MovieListActivity : BaseActivity() {

  private val viewModel: MovieListViewModel by viewModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_movie_list)

    setUpActionBar()

    setUpRefreshView()

    setUpViewModel()
  }

  private fun setUpActionBar() {
    setSupportActionBar(toolbar)
  }

  private fun setUpRefreshView() {
    refreshLayout.setColorSchemeResources(
      R.color.colorPrimary,
      R.color.colorPrimaryDark,
      R.color.colorAccent
    )
    refreshLayout.setOnRefreshListener { viewModel.refresh() }
  }

  private fun setUpViewModel() {
    viewModel.movieModels.observe(this, Observer { moviesResource ->
      when (moviesResource.status) {
        Status.LOADING -> {}
        Status.ERROR -> showErrorMessage(moviesResource.exception?.message!!)
        Status.SUCCESS -> {
          showMovies(moviesResource.data!!)
        }
      }
    })
    viewModel.load()
  }

  private fun showMovies(movieModels: List<MovieModel>) {
    refreshLayout.isRefreshing = false
    recyclerView.adapter =
      MoviesAdapter(movieModels = movieModels, listener = { onItemClick(it) })
  }

  private fun onItemClick(movieModel: MovieModel) {
    navigateToDetail(movieModel.id)
  }
}
