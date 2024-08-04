package com.rtga.di

import com.rtga.data.datasource.AuthDataSource
import com.rtga.data.datasource.local.AuthGenerator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AuthRepositoryModule {

    @Provides
    fun provideAuthDataSource(): AuthDataSource {
        return AuthGenerator
    }
}