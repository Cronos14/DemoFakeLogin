package com.rtga.fakelogin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rtga.domain.exception.LoginException
import com.rtga.domain.models.User
import com.rtga.domain.usecase.AuthUseCase
import com.rtga.fakelogin.uistate.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: AuthUseCase
) : ViewModel() {

    private val _loginUiState = MutableStateFlow<UiState<User>>(UiState.Loading(false))
    val loginUiState: StateFlow<UiState<User>> = _loginUiState.asStateFlow()

    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                loginUseCase(username, password).collect {
                    _loginUiState.value = UiState.Success(it)
                }
            } catch (e: LoginException) {
                _loginUiState.value = UiState.Error(e)
            }
        }
    }

    fun showProgress(show: Boolean) {
        _loginUiState.value = UiState.Loading(show)
    }
}