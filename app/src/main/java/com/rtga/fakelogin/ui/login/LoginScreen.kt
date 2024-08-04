package com.rtga.fakelogin.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.rtga.domain.exception.LoginException
import com.rtga.domain.models.User
import com.rtga.fakelogin.R
import com.rtga.fakelogin.ui.alert.AlertData
import com.rtga.fakelogin.ui.alert.AlertDialog
import com.rtga.fakelogin.uistate.UiState

const val TAG_TEST_PROGRESS_INDICATOR = "progressIndicator"

@Composable
fun LoginScreen(
    loginUiState: UiState<User>,
    onClickLogin: (username: String, password: String) -> Unit,
    onClickAlert: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text(stringResource(R.string.login_username)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(stringResource(R.string.login_password)) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            ShowState(loginUiState, onClickAlert)

            Button(
                onClick = {
                    onClickLogin(username, password)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.login))
            }
        }
    }
}

@Composable
private fun ShowState(
    loginUiState: UiState<User>,
    onClickAlert: () -> Unit
) {
    when (loginUiState) {
        is UiState.Loading -> {
            if (loginUiState.showProgress) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.testTag(TAG_TEST_PROGRESS_INDICATOR)
                    )
                }
            }
        }

        is UiState.Error -> {
            val message = when (loginUiState.error) {
                is LoginException.IncorrectDataException -> stringResource(R.string.user_or_password_incorrect)
                is LoginException.UserNotFoundException -> stringResource(R.string.user_not_found)
                is LoginException.UserEmptyException -> stringResource(R.string.use_empty)
                is LoginException.PasswordEmptyException -> stringResource(R.string.password_empty)
                else -> stringResource(R.string.something_is_wrong)
            }
            Text(text = message, color = Color.Red)
        }

        is UiState.Success -> {
            AlertDialog(
                AlertData(
                    stringResource(R.string.dialog_title),
                    loginUiState.data.name,
                    stringResource(R.string.dialog_confirm),
                    stringResource(R.string.dialog_dismiss)
                ),
                onDismiss = {
                    onClickAlert()
                },
                onConfirm = {
                    onClickAlert()
                }
            )
        }
    }
}