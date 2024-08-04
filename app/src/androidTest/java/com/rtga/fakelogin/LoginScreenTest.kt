package com.rtga.fakelogin

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rtga.domain.models.User
import com.rtga.fakelogin.ui.login.LoginScreen
import com.rtga.fakelogin.uistate.UiState
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testLoginScreenDisplaysCorrectly() {
        composeTestRule.setContent {
            LoginScreen(
                loginUiState = UiState.Loading(false),
                onClickLogin = { _, _ -> },
                onClickAlert = {}
            )
        }

        composeTestRule.onNodeWithText("Username").assertExists()
        composeTestRule.onNodeWithText("Password").assertExists()
        composeTestRule.onNodeWithText("Login").assertExists()
    }

    @Test
    fun testLoginButtonClick() {
        var loginClicked = false

        composeTestRule.setContent {
            LoginScreen(
                loginUiState = UiState.Loading(false),
                onClickLogin = { _, _ ->
                    loginClicked = true
                },
                onClickAlert = {}
            )
        }

        composeTestRule.onNodeWithText("Username").performTextInput("testuser")
        composeTestRule.onNodeWithText("Password").performTextInput("password")
        composeTestRule.onNodeWithText("Login").performClick()

        assert(loginClicked)
    }

    @Test
    fun testShowLoadingState() {
        composeTestRule.setContent {
            LoginScreen(
                loginUiState = UiState.Loading(true),
                onClickLogin = { _, _ -> },
                onClickAlert = {}
            )
        }

        composeTestRule.onNodeWithTag("progressIndicator").assertExists()
    }

    @Test
    fun testShowErrorState() {
        composeTestRule.setContent {
            LoginScreen(
                loginUiState = UiState.Error("Login failed"),
                onClickLogin = { _, _ -> },
                onClickAlert = {}
            )
        }

        composeTestRule.onNodeWithText("Login failed").assertExists()
    }

    @Test
    fun testShowSuccessState() {
        composeTestRule.setContent {
            LoginScreen(
                loginUiState = UiState.Success(User("testuser", "12345")),
                onClickLogin = { _, _ -> },
                onClickAlert = {}
            )
        }

        composeTestRule.onNodeWithText("Welcome").assertExists()
        composeTestRule.onNodeWithText("testuser").assertExists()
    }
}