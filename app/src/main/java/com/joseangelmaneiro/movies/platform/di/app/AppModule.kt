package com.joseangelmaneiro.movies.platform.di.app

import android.app.Application
import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import com.joseangelmaneiro.movies.data.MoviesRepositoryImpl
import com.joseangelmaneiro.movies.data.entity.mapper.MovieMapper
import com.joseangelmaneiro.movies.data.executor.JobThread
import com.joseangelmaneiro.movies.data.source.local.MoviesDatabaseHelper
import com.joseangelmaneiro.movies.data.source.local.MoviesLocalDataSource
import com.joseangelmaneiro.movies.data.source.local.MoviesLocalDataSourceImpl
import com.joseangelmaneiro.movies.data.source.remote.MoviesRemoteDataSource
import com.joseangelmaneiro.movies.data.source.remote.MoviesRemoteDataSourceImpl
import com.joseangelmaneiro.movies.data.source.remote.MovieService
import com.joseangelmaneiro.movies.domain.MoviesRepository
import com.joseangelmaneiro.movies.domain.executor.JobScheduler
import com.joseangelmaneiro.movies.domain.executor.UIScheduler
import com.joseangelmaneiro.movies.platform.executor.UIThread
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class AppModule {

    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    internal fun provideSQLiteOpenHelper(context: Context): SQLiteOpenHelper {
        return MoviesDatabaseHelper(context)
    }

    @Provides
    @Singleton
    internal fun provideService(): MovieService {
        return Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create<MovieService>(MovieService::class.java)
    }

    @Provides
    @Singleton
    internal fun provideLocalDataSource(sqLiteOpenHelper: SQLiteOpenHelper): MoviesLocalDataSource {
        return MoviesLocalDataSourceImpl(sqLiteOpenHelper)
    }

    @Provides
    @Singleton
    internal fun provideRemoteDataSource(movieService: MovieService): MoviesRemoteDataSource {
        return MoviesRemoteDataSourceImpl(movieService)
    }

    @Provides
    @Singleton
    internal fun provideRepository(localDataSource: MoviesLocalDataSource,
                                   remoteDataSource: MoviesRemoteDataSource,
                                   movieMapper: MovieMapper): MoviesRepository {
        return MoviesRepositoryImpl(localDataSource, remoteDataSource, movieMapper)
    }

    @Provides
    @Singleton
    internal fun provideUIScheduler(): UIScheduler {
        return UIThread()
    }

    @Provides
    @Singleton
    internal fun provideJobScheduler(): JobScheduler {
        return JobThread()
    }

}
