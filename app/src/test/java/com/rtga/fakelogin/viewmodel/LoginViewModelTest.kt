package com.rtga.fakelogin.viewmodel

import com.rtga.domain.exception.LoginException
import com.rtga.domain.models.User
import com.rtga.domain.usecase.AuthUseCase
import com.rtga.fakelogin.uistate.UiState
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.UUID

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    @Mock
    private lateinit var mockAuthUseCase: AuthUseCase
    private lateinit var loginViewModel: LoginViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        loginViewModel = LoginViewModel(mockAuthUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `login success updates state with success`() = runTest {
        val token = UUID.randomUUID().toString()
        val username = "username1"
        val password = "12345"
        val mockUser = User("User1", token)
        Mockito.`when`(mockAuthUseCase(username, password)).thenReturn(flowOf(mockUser))

        loginViewModel.login(username, password)

        advanceUntilIdle()

        val stateFlow = loginViewModel.loginUiState
        val successState = stateFlow.first()
        assertEquals(UiState.Success(mockUser), successState)
    }

    @Test
    fun `login failure updates state with error`() = runTest {
        val username = "user"
        val password = "pass"
        val error = LoginException.IncorrectDataException
        Mockito.`when`(mockAuthUseCase(username, password))
            .thenReturn(flow { throw LoginException.IncorrectDataException })

        loginViewModel.login(username, password)

        advanceUntilIdle()

        val stateFlow = loginViewModel.loginUiState
        val errorState = stateFlow.first()
        assertEquals(UiState.Error(error), errorState)
    }
}