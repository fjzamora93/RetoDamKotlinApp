package com.unir.conexionapirest.di

import com.roleapp.auth.domain.repository.AuthRepository
import com.roleapp.auth.security.TokenManager
import com.unir.conexionapirest.data.repository.AuthRepositoryImpl
import com.unir.conexionapirest.data.repository.Repository
import com.unir.conexionapirest.data.service.ApiService
import com.unir.conexionapirest.data.service.AuthApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }



    @Singleton
    @Provides
    fun provideAuthRepository(
        api: AuthApiService,
        tokenManager: TokenManager,
    ): AuthRepository {
        return AuthRepositoryImpl(api, tokenManager)
    }

    @Singleton
    @Provides
    fun provideRepository(
        api: ApiService,
    ): Repository {
        return Repository(api)
    }

}
