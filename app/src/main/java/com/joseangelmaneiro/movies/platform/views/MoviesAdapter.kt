package com.joseangelmaneiro.movies.platform.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.joseangelmaneiro.movies.R
import com.joseangelmaneiro.movies.domain.model.Movie
import com.squareup.picasso.Picasso

class MoviesAdapter(
  private val movies: List<Movie>,
  private val listener: (Movie) -> Unit
) : RecyclerView.Adapter<MoviesAdapter.MovieHolder>() {

  override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MovieHolder {
    if (viewGroup is RecyclerView) {
      val view = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.item_movie, viewGroup, false)
      return MovieHolder(view)
    } else {
      throw RuntimeException("Not bound to RecyclerView")
    }
  }

  override fun onBindViewHolder(movieHolder: MovieHolder, position: Int) {
    movieHolder.bind(movies[position])
  }

  override fun getItemCount(): Int {
    return movies.size
  }

  inner class MovieHolder(
    itemView: View
  ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private val imageView = itemView.findViewById(R.id.image) as ImageView

    init {
      itemView.setOnClickListener(this)
    }

    fun bind(movie: Movie) {
      Picasso.with(imageView.context)
        .load(movie.posterPath)
        .placeholder(R.drawable.movie_placeholder)
        .into(imageView)
    }

    override fun onClick(view: View) {
      listener(movies[adapterPosition])
    }
  }
}