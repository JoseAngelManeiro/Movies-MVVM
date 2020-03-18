package com.joseangelmaneiro.movies.platform

import com.joseangelmaneiro.movies.platform.di.app.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication


class MoviesApp : DaggerApplication() {

  override fun applicationInjector(): AndroidInjector<MoviesApp> {
    val component = DaggerAppComponent.builder().application(this).build();
    component.inject(this);
    return component
  }

}