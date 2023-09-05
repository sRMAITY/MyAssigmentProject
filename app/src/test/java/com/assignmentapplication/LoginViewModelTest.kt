package com.assignmentapplication

import com.assignmentapplication.data.repository.AuthRepository
import com.assignmentapplication.utils.NetworkHelper
import com.assignmentapplication.viewmodel.LogInViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery

import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class LoginViewModelTest {
    private lateinit var classUnderTest: LogInViewModel

    private val authRepository : AuthRepository = mockk()

    private  val networkHelper : NetworkHelper = mockk()


    init {
        MockKAnnotations.init()
        classUnderTest = LogInViewModel(authRepository,networkHelper)
    }

    @Test
    fun `loginUser test` (){
         val userEmail = "dummyEmail"
         val password = "123456"
         coEvery { networkHelper.isNetworkConnected() } returns  true
        // runBlocking { classUnderTest.loginUser(userEmail,password) }
    }
}