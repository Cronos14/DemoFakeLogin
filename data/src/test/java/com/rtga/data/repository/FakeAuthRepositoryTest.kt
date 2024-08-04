package com.rtga.data.repository

import com.rtga.data.datasource.AuthDataSource
import com.rtga.domain.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.StandardTestDispatcher
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
class FakeAuthRepositoryTest {
    @Mock
    private lateinit var mockAuthDataSource: AuthDataSource

    private lateinit var fakeAuthRepository: FakeAuthRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeAuthRepository = FakeAuthRepository(testDispatcher, mockAuthDataSource)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `invoke with username and password success`() = runTest {
        val username = "username1"
        val password = "12345"
        Mockito.`when`(mockAuthDataSource.getUser(username, password))
            .thenReturn(User("User1", UUID.randomUUID().toString()))

        fakeAuthRepository.login(username, password).collect()

        Mockito.verify(mockAuthDataSource).getUser(username, password)
    }
}