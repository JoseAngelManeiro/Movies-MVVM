package com.joseangelmaneiro.movies.platform.views

import android.os.Bundle
import com.joseangelmaneiro.movies.R
import com.joseangelmaneiro.movies.domain.model.Movie
import com.joseangelmaneiro.movies.platform.navigateToDetail
import com.joseangelmaneiro.movies.presentation.presenters.MovieListPresenter
import com.joseangelmaneiro.movies.presentation.MovieListView
import kotlinx.android.synthetic.main.activity_movie_list.*
import org.koin.android.ext.android.inject

class MovieListActivity : BaseActivity(), MovieListView {

  private val presenter: MovieListPresenter by inject()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_movie_list)

    setUpActionBar()

    setUpRefreshView()

    informPresenterViewIsReady()
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
    refreshLayout.setOnRefreshListener { presenter.refresh() }
  }

  private fun informPresenterViewIsReady() {
    presenter.setView(this)
    presenter.viewReady()
  }

  override fun showMovies(movies: List<Movie>) {
    recyclerView.adapter = MoviesAdapter(
      movies = movies,
      listener = { presenter.onItemClick(it) }
    )
  }

  override fun cancelRefreshDialog() {
    refreshLayout.isRefreshing = false
  }

  override fun navigateToDetailScreen(movieId: Int) {
    navigateToDetail(movieId)
  }
}
