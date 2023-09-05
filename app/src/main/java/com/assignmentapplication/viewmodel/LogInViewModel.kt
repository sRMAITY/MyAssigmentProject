package com.assignmentapplication.viewmodel



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignmentapplication.data.repository.AuthRepository
import com.assignmentapplication.utils.AppSheardPreference
import com.assignmentapplication.utils.MyCustomException
import com.assignmentapplication.utils.NetworkHelper
import com.assignmentapplication.utils.Resource
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LogInViewModel @Inject constructor(  private val repository: AuthRepository ,
                                           private val networkHelper: NetworkHelper,
                                           val appSheardPreference: AppSheardPreference): ViewModel() {

    private val _loginFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val loginFlow: StateFlow<Resource<FirebaseUser>?> = _loginFlow


    /**
     *  internet check and call firebase authenticate user
     */
    fun loginUser(email: String, password: String) = viewModelScope.launch {
        if (networkHelper.isNetworkConnected()) {
              _loginFlow.value = Resource.Loading
              val result = repository.login(email, password)
             _loginFlow.value = result
        }
        else
            _loginFlow.value = Resource.Failure(MyCustomException("No internet connection"))

    }
}