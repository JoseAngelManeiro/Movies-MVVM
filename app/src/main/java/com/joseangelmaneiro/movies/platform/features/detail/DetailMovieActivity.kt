package com.joseangelmaneiro.movies.platform.features.detail

import android.os.Bundle
import android.view.MenuItem
import com.joseangelmaneiro.movies.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_movie.*
import kotlinx.android.synthetic.main.content_detail_movie.*
import android.content.Intent
import android.app.Activity
import androidx.lifecycle.Observer
import com.joseangelmaneiro.movies.platform.Formatter
import com.joseangelmaneiro.movies.platform.Status
import com.joseangelmaneiro.movies.platform.features.BaseActivity
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class DetailMovieActivity : BaseActivity() {

  private val viewModel: DetailMovieViewModel by viewModel()
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

    setUpViewModel()
  }

  private fun setUpViewModel() {
    val movieId = intent.getIntExtra(EXTRA_MOVIE_ID, -1)
    if (movieId != -1) {
      viewModel.movieDetailModel.observe(this, Observer { movieResource ->
        when (movieResource.status) {
          Status.LOADING -> {}
          Status.ERROR -> showErrorMessage(movieResource.exception?.message!!)
          Status.SUCCESS -> displayMovieDetailModel(movieResource.data!!)
        }
      })
      viewModel.load(movieId)
    } else {
      throw RuntimeException("Data not sent to " + this::class.java.simpleName)
    }
  }

  private fun displayMovieDetailModel(movieDetailModel: MovieDetailModel) {
    setUpActionBar(movieDetailModel.title)
    Picasso.with(this)
      .load(movieDetailModel.backdropPath)
      .into(image_movie)
    text_voteAverage.text = movieDetailModel.voteAverage
    text_releaseDate.text = formatter.formatDate(movieDetailModel.releaseDate)
    text_overview.text = movieDetailModel.overview
  }

  private fun setUpActionBar(title: String) {
    setSupportActionBar(toolbar)
    supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    setTitle(title)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      android.R.id.home -> {
        onBackPressed()
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }
}
