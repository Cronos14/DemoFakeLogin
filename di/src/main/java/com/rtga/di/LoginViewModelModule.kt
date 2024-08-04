package com.rtga.di

import com.rtga.domain.repository.AuthRepository
import com.rtga.domain.usecase.AuthUseCase
import com.rtga.domain.usecase.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object LoginViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideLoginUseCase(authRepository: AuthRepository): AuthUseCase {
        return LoginUseCase(authRepository)
    }
}