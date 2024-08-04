package com.rtga.domain.usecase

import com.rtga.domain.models.User
import com.rtga.domain.repository.AuthRepository
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.util.UUID
import kotlin.test.assertFailsWith

@RunWith(MockitoJUnitRunner::class)
class LoginUseCaseTest {

    @Mock
    private lateinit var mockAuthRepository: AuthRepository

    private lateinit var loginUseCase: LoginUseCase

    @Before
    fun setUp() {
        loginUseCase = LoginUseCase(mockAuthRepository)
    }

    @Test
    fun `invoke with username and password success`() {
        val username = "username1"
        val password = "12345"
        val userFlow = flowOf(User("User1", UUID.randomUUID().toString()))
        Mockito.`when`(mockAuthRepository.login(username, password)).thenReturn(userFlow)

        loginUseCase(username, password)

        verify(mockAuthRepository).login(username, password)
    }

    @Test
    fun `invoke with empty username throws Exception`() {
        val username = ""
        val password = "12345"

        assertFailsWith<RuntimeException>("User Empty") {
            loginUseCase(username, password)
        }
        verify(mockAuthRepository, never()).login(username, password)
    }

    @Test
    fun `invoke with empty password throws Exception`() {
        val username = "username1"
        val password = ""

        assertFailsWith<RuntimeException>("Password Empty") {
            loginUseCase(username, password)
        }
        verify(mockAuthRepository, never()).login(username, password)
    }

}