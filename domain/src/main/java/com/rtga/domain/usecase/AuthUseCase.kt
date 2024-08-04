package com.rtga.domain.usecase

import com.rtga.domain.models.User
import kotlinx.coroutines.flow.Flow

interface AuthUseCase {
    operator fun invoke(username: String, password: String): Flow<User>
}