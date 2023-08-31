package com.assignmentapplication.viewmodel


import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignmentapplication.data.repository.AuthRepository
import com.assignmentapplication.utils.MyCustomException
import com.assignmentapplication.utils.NetworkHelper
import com.assignmentapplication.utils.Resource
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LogInViewModel @Inject constructor(  private val repository: AuthRepository ,
                                           private val networkHelper: NetworkHelper ): ViewModel() {

    private val _loginFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val loginFlow: StateFlow<Resource<FirebaseUser>?> = _loginFlow


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