package com.rtga.domain.exception

sealed class LoginException : RuntimeException() {
    object IncorrectDataException : LoginException()
    object UserNotFoundException : LoginException()
    object UserEmptyException : LoginException()
    object PasswordEmptyException : LoginException()
}