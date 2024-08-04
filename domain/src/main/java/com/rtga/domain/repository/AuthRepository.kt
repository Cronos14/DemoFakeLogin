package com.rtga.domain.repository

import com.rtga.domain.models.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(username: String, password: String): Flow<User>
}