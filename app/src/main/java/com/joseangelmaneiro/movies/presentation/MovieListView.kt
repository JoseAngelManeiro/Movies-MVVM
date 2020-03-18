package com.joseangelmaneiro.movies.presentation


interface MovieListView : BaseView {

  fun refreshList()

  fun cancelRefreshDialog()

  fun navigateToDetailScreen(movieId: Int)

}