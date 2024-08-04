package com.rtga.domain.usecase

import com.rtga.domain.exception.LoginException
import com.rtga.domain.models.User
import com.rtga.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class LoginUseCase(
    private val authRepository: AuthRepository
) : AuthUseCase {
    override fun invoke(username: String, password: String): Flow<User> {
        if (username.isEmpty()) throw LoginException.UserEmptyException
        if (password.isEmpty()) throw LoginException.PasswordEmptyException
        return authRepository.login(username, password)
    }
}