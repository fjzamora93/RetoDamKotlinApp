package com.unir.conexionapirest.di

import android.content.Context
import androidx.room.Room
import com.unir.conexionapirest.data.database.MovieApiService

import com.unir.conexionapirest.data.database.MovieDao
import com.unir.conexionapirest.data.database.MovieDatabase
import com.unir.conexionapirest.data.repository.LocalMovieRepository
import com.unir.conexionapirest.data.repository.MovieRepository
import com.unir.conexionapirest.data.repository.RemoteMovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


/**INYECCIÓN DE DEPENDENCIAS (DI).
 *
 * Esta clase nos va a dar una serie de métodos que nos van a permitir
 * gestionar las dependencias de la aplicación sin tener que estar instanciándolas
 * una y otra vez.
 *
 * */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /** INSTANCIA ÚNICA DE RETROFIT + CONVERTIDOR JSON */
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.omdbapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /** INSTANCIA ÚNICA DEL SERVICIO API */
    @Provides
    @Singleton
    fun provideMovieApiService(retrofit: Retrofit): MovieApiService {
        return retrofit.create(MovieApiService::class.java)
    }

    /** INSTANCIA ÚNICA DEL REPOSITORIO UNIFICADO (LOCAL + REMOTO) */
    @Provides
    @Singleton
    fun provideMovieRepository(
        localMovieRepository: LocalMovieRepository,
        remoteMovieRepository: RemoteMovieRepository
    ): MovieRepository {
        return MovieRepository(localMovieRepository, remoteMovieRepository)
    }

    /** INSTANCIA ÚNICA DEL REPOSITORIO REMOTO */
    @Provides
    @Singleton
    fun provideMovieRemoteRepository(movieApiService: MovieApiService): RemoteMovieRepository {
        return RemoteMovieRepository(movieApiService)
    }

    /** INSTANCIA ÚNICA DE LA BASE DE DATOS SQLITE LOCAL */
    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "movie_database"
        ).build()
    }

    /** INSTANCIA ÚNICA DEL DAO, QUE VA A LA BASE DE DATOS LOCAL */
    @Provides
    @Singleton
    fun provideMovieDao(movieDatabase: MovieDatabase): MovieDao {
        return movieDatabase.getMovieDao()
    }

    /** INSTANCIA ÚNICA DEL REPOSITORIO LOCAL */
    @Provides
    @Singleton
    fun provideLocalMovieRepository(movieDao: MovieDao): LocalMovieRepository {
        return LocalMovieRepository(movieDao)
    }
}