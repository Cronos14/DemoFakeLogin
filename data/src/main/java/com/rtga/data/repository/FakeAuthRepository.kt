package com.rtga.data.repository

import com.rtga.data.datasource.AuthDataSource
import com.rtga.domain.models.User
import com.rtga.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FakeAuthRepository(
    private val dispatcher: CoroutineDispatcher,
    private val authDataSource: AuthDataSource
) : AuthRepository {

    override fun login(username: String, password: String): Flow<User> = flow {
        emit(authDataSource.getUser(username, password))
    }.flowOn(dispatcher)
}