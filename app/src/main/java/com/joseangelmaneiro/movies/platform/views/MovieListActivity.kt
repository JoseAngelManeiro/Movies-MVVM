package com.joseangelmaneiro.movies.platform.views

import android.os.Bundle
import com.joseangelmaneiro.movies.R
import com.joseangelmaneiro.movies.platform.navigateToDetail
import com.joseangelmaneiro.movies.presentation.presenters.MovieListPresenter
import com.joseangelmaneiro.movies.presentation.MovieListView
import kotlinx.android.synthetic.main.activity_movie_list.*
import javax.inject.Inject

class MovieListActivity : BaseActivity(), MovieListView {

    @Inject lateinit var presenter: MovieListPresenter

    private lateinit var adapter: MoviesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        setUpActionBar()

        setUpListView()

        setUpRefreshView()

        informPresenterViewIsReady()
    }

    private fun setUpActionBar() {
        setSupportActionBar(toolbar)
    }

    private fun setUpListView() {
        adapter = MoviesAdapter(presenter)
        recyclerView.adapter = adapter
    }

    private fun setUpRefreshView() {
        refreshLayout.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.colorPrimaryDark,
                R.color.colorAccent)
        refreshLayout.setOnRefreshListener { presenter.refresh() }
    }

    private fun informPresenterViewIsReady() {
        presenter.viewReady()
    }

    override fun refreshList() {
        adapter.refreshData()
    }

    override fun cancelRefreshDialog() {
        refreshLayout.isRefreshing = false
    }

    override fun navigateToDetailScreen(movieId: Int) {
        navigateToDetail(movieId)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }
}
