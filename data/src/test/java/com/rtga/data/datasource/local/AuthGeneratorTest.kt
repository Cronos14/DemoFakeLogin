package com.rtga.data.datasource.local

import com.rtga.domain.exception.LoginException
import junit.framework.TestCase.assertEquals
import org.junit.Test
import kotlin.test.assertFailsWith

class AuthGeneratorTest {
    @Test
    fun `auth with username and password success`() {
        val user = AuthGenerator.getUser("username1", "12345")
        assertEquals("User1", user.name)
    }

    @Test
    fun `auth with username incorrect throws Exception`() {
        assertFailsWith<LoginException.IncorrectDataException> {
            AuthGenerator.getUser("otherUser", "12345")
        }
    }

    @Test
    fun `auth with password incorrect throws Exception`() {
        assertFailsWith<LoginException.IncorrectDataException> {
            AuthGenerator.getUser("username1", "09796")
        }
    }

    @Test
    fun `auth with username not found throws Exception`() {
        assertFailsWith<LoginException.UserNotFoundException>("User not found") {
            AuthGenerator.getUser("username3", "54312")
        }
    }
}