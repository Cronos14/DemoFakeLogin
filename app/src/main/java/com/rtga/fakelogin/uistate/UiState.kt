package com.rtga.fakelogin.uistate

sealed class UiState<out T> {
    data class Loading(val showProgress: Boolean) : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val error: Exception) : UiState<Nothing>()
}