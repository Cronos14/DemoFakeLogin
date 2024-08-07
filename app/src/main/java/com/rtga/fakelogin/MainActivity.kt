package com.rtga.fakelogin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.rtga.domain.models.User
import com.rtga.fakelogin.ui.login.LoginScreen
import com.rtga.fakelogin.ui.theme.FakeLoginTheme
import com.rtga.fakelogin.uistate.UiState
import com.rtga.fakelogin.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FakeLoginTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(loginViewModel)
                }
            }
        }
    }
}

@Composable
fun MainScreen(loginViewModel: LoginViewModel) {
    val loginUiState: UiState<User> by loginViewModel.loginUiState.collectAsState()
    LoginScreen(loginUiState = loginUiState, onClickLogin = { username, password ->
        loginViewModel.showProgress(true)
        loginViewModel.login(username, password)
    }, {
        loginViewModel.showProgress(false)
    })
}