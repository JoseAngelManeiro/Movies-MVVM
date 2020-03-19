package com.joseangelmaneiro.movies.platform.di

import com.joseangelmaneiro.movies.platform.di.detail.DetailActivityModule
import com.joseangelmaneiro.movies.platform.di.list.ListActivityModule
import com.joseangelmaneiro.movies.platform.views.DetailMovieActivity
import com.joseangelmaneiro.movies.platform.views.MovieListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

  @ContributesAndroidInjector(modules = [(ListActivityModule::class)])
  @PerActivity
  internal abstract fun bindListActivity(): MovieListActivity

  @ContributesAndroidInjector(modules = [(DetailActivityModule::class)])
  @PerActivity
  internal abstract fun bindDetailActivity(): DetailMovieActivity

}