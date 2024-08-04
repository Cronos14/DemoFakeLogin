package com.rtga.di

import com.rtga.data.datasource.AuthDataSource
import com.rtga.data.repository.FakeAuthRepository
import com.rtga.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
object LoginUseCaseModule {

    @Provides
    fun provideAuthRepository(
        @IoDispatcher dispatcher: CoroutineDispatcher,
        authDataSource: AuthDataSource
    ): AuthRepository {
        return FakeAuthRepository(dispatcher, authDataSource)
    }

}