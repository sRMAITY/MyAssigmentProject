package com.assignmentapplication

import com.assignmentapplication.data.repository.AuthRepository
import com.assignmentapplication.utils.NetworkHelper
import com.assignmentapplication.viewmodel.LogInViewModel
import io.mockk.MockKAnnotations

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
         runBlocking { classUnderTest.loginUser("a@a.com","123456") }
    }
}