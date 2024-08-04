package com.rtga.data.datasource.local

import com.rtga.data.datasource.AuthDataSource
import com.rtga.domain.exception.LoginException
import com.rtga.domain.models.User
import java.util.UUID

object AuthGenerator : AuthDataSource {

    private val fakeUserData = mapOf(
        "username1" to "12345",
        "username2" to "54321",
        "username3" to "54312",
    )

    private val fakeUsers = mapOf(
        "username1" to User("User1", UUID.randomUUID().toString()),
        "username2" to User("User2", UUID.randomUUID().toString()),
    )

    override fun getUser(username: String, password: String): User {
        if (fakeUserData[username] != password) throw LoginException.IncorrectDataException
        return fakeUsers[username] ?: throw LoginException.UserNotFoundException
    }
}