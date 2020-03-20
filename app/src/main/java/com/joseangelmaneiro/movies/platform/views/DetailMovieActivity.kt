package com.joseangelmaneiro.movies.platform.views

import android.os.Bundle
import android.view.MenuItem
import com.joseangelmaneiro.movies.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_movie.*
import kotlinx.android.synthetic.main.content_detail_movie.*
import android.content.Intent
import android.app.Activity
import com.joseangelmaneiro.movies.domain.model.Movie
import com.joseangelmaneiro.movies.presentation.presenters.DetailMoviePresenter
import com.joseangelmaneiro.movies.presentation.DetailMovieView
import com.joseangelmaneiro.movies.presentation.formatters.Formatter
import org.koin.android.ext.android.inject

class DetailMovieActivity : BaseActivity(), DetailMovieView {

  private val presenter: DetailMoviePresenter by inject()
  private val formatter: Formatter by inject()

  companion object {
    const val EXTRA_MOVIE_ID = "MOVIE_ID"
    fun launch(activity: Activity, movieId: Int) {
      val intent = Intent(activity, DetailMovieActivity::class.java)
      intent.putExtra(EXTRA_MOVIE_ID, movieId)
      activity.startActivity(intent)
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_detail_movie)

    informPresenterViewIsReady()
  }

  private fun informPresenterViewIsReady() {
    val movieId = intent.getIntExtra(EXTRA_MOVIE_ID, -1)
    if (movieId != -1) {
      presenter.setView(this)
      presenter.viewReady(movieId)
    }
  }

  override fun displayMovie(movie: Movie) {
    setUpActionBar(movie.title)
    Picasso.with(this)
      .load(movie.backdropPath)
      .into(image_movie)
    text_voteAverage.text = movie.voteAverage
    text_releaseDate.text = formatter.formatDate(movie.releaseDate)
    text_overview.text = movie.overview
  }

  private fun setUpActionBar(title: String) {
    setSupportActionBar(toolbar)
    supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    setTitle(title)
  }

  override fun goToBack() {
    onBackPressed()
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      android.R.id.home -> {
        presenter.navUpSelected()
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }
}
