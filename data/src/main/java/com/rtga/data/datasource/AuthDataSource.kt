package com.rtga.data.datasource

import com.rtga.domain.models.User

interface AuthDataSource {
    fun getUser(username: String, password: String): User
}